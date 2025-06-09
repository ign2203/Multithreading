package org.example;

public  interface SiteVisitCounter {
   int count = 0;

   public static void UnsynchronizedCounter() {
       count++;
   }

}
