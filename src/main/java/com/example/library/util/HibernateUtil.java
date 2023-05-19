package com.example.library.util;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.persistence.EntityManager;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class HibernateUtil {

    private static final SessionFactory sessionFactory;
    private static final String PACKAGE_ENTITIES =
            "com.example.library.entities";
    private static final String HIBERNATE_CONN_URL_PROP = "hibernate.connection.url";
    private static final String HIBERNATE_MYSQL_USER_PROP = "hibernate.connection.username";
    private static final String HIBERNATE_MYSQL_USER_PASS = "hibernate.connection.password";
    private static EntityManager entityManager;

    static {
        var cfg = new Configuration().configure();
        try {
            for (var cls : getEntityClassesFromPackage(PACKAGE_ENTITIES)) {
                cfg.addAnnotatedClass(cls);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getCause());
        }
        loadEnv(cfg);
        new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
        sessionFactory = cfg.buildSessionFactory();
    }

    public static EntityManager getEntityManager() {
        if (entityManager == null) {
            entityManager = sessionFactory.createEntityManager();
        }
        return sessionFactory.createEntityManager();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    private static List<Class<?>> getEntityClassesFromPackage(String packageName)
            throws ClassNotFoundException, URISyntaxException {
        var classNames = getClassNamesFromPackage(packageName);
        var classes = new ArrayList<Class<?>>();
        for (String className : classNames) {
            var cls = Class.forName(packageName + "." + className);
            var annotations = cls.getAnnotations();

            for (var annotation : annotations) {
                if (annotation instanceof jakarta.persistence.Entity) {
                    classes.add(cls);
                }
            }
        }

        return classes;
    }

    private static ArrayList<String> getClassNamesFromPackage(String packageName) throws
            URISyntaxException {
        if (packageName == null) {
            throw new RuntimeException("Package name cannot not be null!");
        }
        var classLoader = Thread.currentThread().getContextClassLoader();
        var names = new ArrayList<String>();

        packageName = packageName.replace(".", "/");
        var packageURL = classLoader.getResource(packageName);

        if (packageURL == null) {
            throw new RuntimeException("Could not locate package: " + packageName);
        }

        var uri = new URI(packageURL.toString());
        var folder = new File(uri.getPath());
        var files = folder.listFiles();
        if (files == null || files.length == 0) {
            throw new RuntimeException("Package name: " + packageName + " is empty!");
        }

        for (var file : files) {
            String name = file.getName();
            name = name.substring(0, name.lastIndexOf('.'));  // remove ".class"
            names.add(name);
        }
        return names;
    }

    private static void loadEnv(Configuration configuration) {
        Dotenv dotenv = Dotenv.load();
        String connectionUrl = "jdbc:mysql://" + dotenv.get("DATABASE_HOST") + ":" + dotenv.get("DATABASE_PORT")
                + "/" + dotenv.get("DATABASE_NAME") + "?useSSL=false";
        var hibernateProps = configuration.getProperties();
        hibernateProps.put(HIBERNATE_CONN_URL_PROP, connectionUrl);
        hibernateProps.put(HIBERNATE_MYSQL_USER_PROP, dotenv.get("DATABASE_USERNAME"));
        hibernateProps.put(HIBERNATE_MYSQL_USER_PASS, dotenv.get("DATABASE_PASSWORD"));
    }
}
