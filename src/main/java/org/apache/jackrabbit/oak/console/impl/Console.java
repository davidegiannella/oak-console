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

package org.apache.jackrabbit.oak.console.impl;

import java.util.Collections;
import java.util.LinkedList;

import javax.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;
import org.apache.jackrabbit.oak.api.PropertyState;
import org.apache.jackrabbit.oak.spi.state.NodeState;
import org.apache.jackrabbit.oak.spi.state.NodeStore;

public abstract class Console {
   NodeStore store;
   NodeState root;
   
   public static enum Persistence {
      TAR("tar"),H2("h2");

      private final String type;
      private Persistence(String type){
         this.type = type;
      }

      public static boolean contains(String type){
         return (fromString(type)!=null);
      }

      public static @Nullable Persistence fromString(String type){
         for(Persistence p : Persistence.values()){
            if(p.type.equalsIgnoreCase(type)){
               return p;
            }
         }
         return null;
      }
   };

   
   public static Console getConsole(Persistence type, String repoPath){
      Console c = null;
      switch(type){
      case TAR: 
         c = new TarConsole(repoPath);
         break;
      case H2:
         c = new H2Console(repoPath);
         break;
      default:
         break;
      }
      return c;
   }
   
   /**
    * lists the children of the root
    */
   public void list(){
      printNode(root);
   }
   
   /**
    * list the children of a given path
    * @param path
    */
   public void list(String path){
      LinkedList<String> nodes = new LinkedList<String>();
      NodeState leaf = null;
      
      Collections.addAll(nodes, path.split("/"));
      leaf = browseDown(root, nodes);
      if(leaf!=null){
         printNode(leaf);
      }
   }
   
   
   private void printNode(NodeState node){
      System.out.println("{");
      for(PropertyState property : node.getProperties()){
         System.out.println("  " + property.toString() + ",");
      }
      System.out.println("}");
      
      for(String child : node.getChildNodeNames()){
         if(node.getChildNode(child).getChildNodeNames().iterator().hasNext()) {
            System.out.print("+ ");
         }else{
            System.out.print("  ");
         }
         System.out.println(child);
      }
   }
      
   private NodeState browseDown(NodeState father, LinkedList<String> path){
      String node = path.poll();
      NodeState child = null;
      
      if(!StringUtils.isEmpty(node)){
         if(father.hasChildNode(node)){
            child = father.getChildNode(node);
         }else{
            System.err.println("Wrong path. Node doesn't exists. " + node);
            return null;
         }
      }else{
         child = father;
      }
      
      if(!path.isEmpty()){
         child = browseDown(child, path);
      }
      
      return child;
   }
   
   /**
    * implement when the store need to be closed
    */
   public abstract void close();
}
