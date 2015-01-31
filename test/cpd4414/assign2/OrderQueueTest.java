/*
 * Copyright 2015 Len Payne <len.payne@lambtoncollege.ca>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cpd4414.assign2;

import cpd4414.assign2.Order;
import cpd4414.assign2.OrderQueue;
import cpd4414.assign2.OrderQueue.noCustomerException;
import cpd4414.assign2.OrderQueue.noPurchasesException;
import cpd4414.assign2.OrderQueue.noTimeRecievedException;
import cpd4414.assign2.Purchase;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Len Payne <len.payne@lambtoncollege.ca>
 */
public class OrderQueueTest {
    
    public OrderQueueTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testWhenCustomerExistsAndPurchasesExistThenTimeReceivedIsNow() {
        OrderQueue orderQueue = new OrderQueue();
        Order order = new Order("CUST00001", "ABC Construction");
        order.addPurchase(new Purchase("PROD0004", 450));
        order.addPurchase(new Purchase("PROD0006", 250));
        try {
        orderQueue.add(order);
        }catch(noCustomerException nce)
        {
            System.out.println("Customer ID and Customer Name Doesnot Exists");
        }
        catch (noPurchasesException npe)
        {
            System.out.println("No Purchases Exists");
        }
        
        long expResult = new Date().getTime();
        long result = order.getTimeReceived().getTime();
        assertTrue(Math.abs(result - expResult) < 1000);
    }
    
    @Test
    public void testWhenNoCustomerExistsThenThrowException() {
        boolean result=false;
        try {
        OrderQueue orderQueue = new OrderQueue();
        Order order = new Order("", "");
        order.addPurchase(new Purchase("PROD0004", 450));
        order.addPurchase(new Purchase("PROD0006", 250));
        orderQueue.add(order);
        
        } catch(noCustomerException nce) 
                { 
                    result=true; 
                }
        catch (noPurchasesException npe)
        {
            System.out.println("No Purchases Exists");
        }
        assertTrue(result);
        
    }
    
    @Test
    public void testWhenNoPurchasesThenThrowException() {
        boolean result=false;
        try {
        OrderQueue orderQueue = new OrderQueue();
        Order order = new Order("CUST00001", "ABC Construction");
        order.addPurchase(new Purchase("", 0));
        order.addPurchase(new Purchase("", 0));
        orderQueue.add(order);
        } catch(noPurchasesException npe)
        {
            result=true;
        }
        catch (noCustomerException nce)
        {
            System.out.println("Customer ID and Customer Name Doesnot Exists");
        }
    }
    
    @Test 
        void testWhenRequestForNextOrderWhenOrdersExistsThenReturnTimeRecievedWithNoTimeProcessed() {
        OrderQueue orderQueue = new OrderQueue();
        Order order = new Order("CUST00001", "ABC Construction");
        order.addPurchase(new Purchase("PROD0004", 450));
        order.addPurchase(new Purchase("PROD0006", 250));
        try {
            orderQueue.add(order);
        } catch (noCustomerException ex) {
            Logger.getLogger(OrderQueueTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (noPurchasesException ex) {
            Logger.getLogger(OrderQueueTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Order order1 = orderQueue.nextOrder();
        assertEquals(order1, order);
        
    }
    
     @Test 
     void testWhenRequestForNextOrderWhenNoOrdersExistsThenReturnNull() {
        OrderQueue orderQueue = new OrderQueue();
        Order order1 =orderQueue.nextOrder();
        assertNull(order1);
            
        }
     
      @Test
      void testWhenRequestToProcessOrderWhenThereIsTimeRecievedAndAllPurchasesAreInStockThenTimeProcessedIsNow() {
        OrderQueue orderQueue = new OrderQueue();
        Order order = new Order("CUST00001", "ABC Construction");
        order.addPurchase(new Purchase("PROD0004", 450));
        order.addPurchase(new Purchase("PROD0006", 250));
        try {
            orderQueue.add(order);
            orderQueue.processOrder();
        }catch(Exception ex)
        {
            System.out.println("Exception");
        }
        
        long expResult = new Date().getTime();
        long result = order.getTimeProcessed().getTime();
        assertTrue(Math.abs(result - expResult) < 1000);
      }
      
      
      @Test
      void testWhenRequestToProcessOrderWhenThereIsNoTimeRecievedThenThrowException() {
        boolean result=false;
        try {
        OrderQueue orderQueue = new OrderQueue();
        Order order = new Order("CUST00001", "ABC Construction");
        order.addPurchase(new Purchase("PROD0004", 450));
        order.addPurchase(new Purchase("PROD0006", 250));
        orderQueue.add(order);
        try {
        orderQueue.processOrder();
        } catch (noTimeRecievedException ntre)
        {
            System.out.println("No Time Recieved");
            result = true;
        }
        } catch (Exception ex)
        {
            System.out.println("Exception");
        }
        assertTrue(result);
        
      }
    
}
