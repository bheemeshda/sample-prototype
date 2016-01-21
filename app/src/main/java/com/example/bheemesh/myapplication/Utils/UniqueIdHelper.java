package com.example.bheemesh.myapplication.Utils;

/**
 * Helps in generating unique ids for fragments and activities
 *
 * @author maruti.borker
 */
public class UniqueIdHelper {
  private static int uniqueIdCounter = 0;
  private static UniqueIdHelper instance;

  public static UniqueIdHelper getInstance() {
    if (instance == null) {
      synchronized (UniqueIdHelper.class) {
        if (instance == null) {
          instance = new UniqueIdHelper();
        }
      }
    }
    return instance;
  }

  public int generateUniqueId() {
    return uniqueIdCounter++;
  }
}
