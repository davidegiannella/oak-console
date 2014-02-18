/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.jackrabbit.oak.console;

import org.apache.jackrabbit.oak.console.impl.Console;

public class Launcher {
   public static void main( String[] args ) {

      if(args.length >= 2 && Console.Persistence.contains(args[0])){
         Console c = Console.getConsole(Console.Persistence.fromString(args[0]),args[1]);

         if(args.length > 2){
            //we are asking a specific path
            c.list(args[2]);
         }else{
            //listing root
            c.list();
         }

         c.close();
      } else {
         System.out.println("java -jar oak-console*.jar <persistence-type> <path-to-repo> [node to list]");
         System.out.print("Peristence types: ");
         for(Console.Persistence p : Console.Persistence.values()){
            System.out.print(String.format("%s, ", p.getType()));
         }
         System.out.println();
      }
   }
}
