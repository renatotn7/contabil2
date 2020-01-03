package br.com.cvm.bd.helper;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
public enum JPAUtil {
  INSTANCE;
  private static EntityManagerFactory emFactory= Persistence.createEntityManagerFactory("jpa-example");;
  private JPAUtil() {
    // "jpa-example" was the value of the name attribute of the
    // persistence-unit element.

  }
  public EntityManager getEntityManager() {
    return emFactory.createEntityManager();
  }
  public void close() {
    emFactory.close();
  }
}