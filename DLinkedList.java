import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * provided file for DLinkedList Assignment
 * 
 * @author lkfritz
 */
public class DLinkedList<T extends Comparable<T>> {
    public static void main(String[] args) throws FileNotFoundException {
        DLinkedList<String> lst1 = new DLinkedList<>();
        DLinkedList<String> lst2 = new DLinkedList<>();
        Scanner fin = new Scanner(new File("text1.in"));
        String str;
        while (fin.hasNext()) {
            str = fin.next();
            str = cleanUp(str);
            lst1.insertOrderUnique(str);
        }
        fin.close();
        fin = new Scanner(new File("text2.in"));
        while (fin.hasNext()) {
            str = fin.next();
            str = cleanUp(str);
            lst2.insertOrderUnique(str);
        }
        System.out.println("List 1:  " + lst1);
        System.out.println("List 2:  " + lst2);
        DLinkedList combined = lst1.merge(lst2);
        System.out.println("\nAFTER MERGE");
        System.out.println("List 1:  " + lst1);
        System.out.println("List 2:  " + lst2);
        System.out.println("\n" + combined);
    }

    /**
     * ASSIGNED
     * 
     * @param str
     * @return str in all lower case with LEADING and TRAILING non-alpha
     *         chars removed
     */
    //O(1)
    public static String cleanUp(String str) {
        str = str.toLowerCase();
        str = str.replaceAll("[!()\\-\\+\\.\\^:,]", "");
        return str;
    }

    // inner DNode class: PROVIDED
    private class DNode {
        private DNode next, prev;
        private T data;

        private DNode(T val) {
            this.data = val;
            next = prev = this;
        }
    }

    // DLinkedList fields: PROVIDED
    private DNode header;

    // create an empty list: PROVIDED
    public DLinkedList() {
        header = new DNode(null);
    }

    /**
     * PROVIDED add
     * 
     * @param item return ref to newly inserted node
     */
    public DNode add(T item) {
        // make a new node
        DNode newNode = new DNode(item);
        // update newNode
        newNode.prev = header;
        newNode.next = header.next;
        // update surrounding nodes
        header.next.prev = newNode;
        header.next = newNode;
        return newNode;
    }

    // PROVIDED
    public String toString() {
        String str = "[";
        DNode curr = header.next;
        while (curr != header) {
            str += curr.data + ", ";
            curr = curr.next;
        }
        if (str.length() > 1) {
            str = str.substring(0, str.length() - 2);
        }
        str += "]";
        return str;
        }

    /**
     * ASSIGNED
     * remove val from the list
     * 
     * @param val
     * @return true if successful, false otherwise
     */
    //O(N)
    public boolean remove(T val) {
        DNode deleteNode = header.next;
        boolean found = false;
        while (deleteNode != header) {
            if (deleteNode.data.equals(val)) {
                found = true;
            }
            deleteNode = deleteNode.next;
        }
        if (!found) {
            return false;
        }
        if (header.next == deleteNode) {
            header.next = deleteNode.next;
        }
        if (deleteNode.next != header) {
            deleteNode.next.prev = deleteNode.prev;
        }
        if (deleteNode.prev != header) {
            deleteNode.prev.next = deleteNode.next;
        }
        return true;
    }

    /**
     * ASSIGNED
     * 
     * @param item
     */
    //O(N)
    public void insertOrder(T item) {
        DNode curr = header.next;
        while (curr != header) {
            if (curr.data.compareTo(item) > 0) {
                break;
            }
            curr = curr.next;
        }
        DNode prev = curr.prev;
        DNode newNode = new DNode(item);
        newNode.prev = prev;
        newNode.next = prev.next;
        prev.next.prev = newNode;
        prev.next = newNode;
    }

    /**
     * ASSIGNED
     * 
     * @param item
     */

    //O(N)
    public boolean insertOrderUnique(T item) {
        DNode curr = header.next;
        while (curr != header && curr.data.compareTo(item) < 0) {
            curr = curr.next;
        }
        DNode prev = curr.prev;
        if (curr != header && curr.data.equals(item)) {
            return false;
        }
        DNode newNode = new DNode(item);
        newNode.prev = prev;
        newNode.next = prev.next;
        prev.next.prev = newNode;
        prev.next = newNode;
        return true;
    }

    /**
     * ASSIGNED
     * PRE: this and rhs are sorted lists
     * 
     * @param rhs
     * @return list that contains this and rhs merged into a sorted list
     *         POST: returned list will not contain duplicates
     */
    public DLinkedList merge(DLinkedList rhs) {
        DLinkedList result = new DLinkedList();
        DNode resHead = result.header.next;
        DNode head1 = header.next;
        DNode head2 = rhs.header.next;
        while (true) {
            if (head1 == header) {
                DNode newNode = new DNode(head2.data);
                if (resHead.data != null && resHead.data.equals(head2.data)) {
                    head2.next = head2;
                    continue;
                }
                newNode.prev = resHead;
                newNode.next = resHead.next;
                resHead.next.prev = newNode;
                resHead.next = newNode;
                head2.next = head2;
                break;
            }
            if (head2 == rhs.header) {
                DNode newNode = new DNode(head1.data);
                if (resHead.data != null && resHead.data.equals(head1.data)) {
                    head1.next = head1;
                    continue;
                }
                newNode.prev = resHead;
                newNode.next = resHead.next;
                resHead.next.prev = newNode;
                resHead.next = newNode;
                head1.next = head1;
                break;
            }

            if (head1.data.compareTo(head2.data) <= 0) {
                DNode newNode = new DNode(head1.data);
                if (resHead.data != null && resHead.data.equals(head1.data)) {
                    head1 = head1.next;
                    continue;
                }
                newNode.prev = resHead;
                newNode.next = resHead.next;
                resHead.next.prev = newNode;
                resHead.next = newNode;
                head1 = head1.next;
            } else {
                DNode newNode = new DNode(head2.data);
                if (resHead.data != null && resHead.data.equals(head2.data)) {
                    head2 = head2.next;
                    continue;
                }
                newNode.prev = resHead;
                newNode.next = resHead.next;
                resHead.next.prev = newNode;
                resHead.next = newNode;
                head2 = head2.next;
            }
            resHead = resHead.next;
        }
        header.next = header;
        rhs.header.next = rhs.header;
        return result;
    }

}