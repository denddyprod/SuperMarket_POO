import java.util.*;

public abstract class ItemList {
    protected Node head;
    private int size;
    protected Comparator c;

    public ItemList(Comparator c) {
        head = null;
        this.c = c;
        size = 0;
    }

    public boolean isEmpty() {
        if (size == 0)
            return true;
        else
            return false;
    }

    public Item[] toArray() {
        Item[] a = new Item[size];
        Node temp = head;
        for (int i = 0; i < size; i++) {
            a[i] = temp.item;
            temp = temp.next;
        }
        return a;
    }


    public Node getNode(int index) {
        Node aux = head;

        for(int i = 0; i < index; i++){
                aux = aux.next;
        }
        return aux;
    }

    public boolean add(Item item) {

        if(isEmpty()){
            head = new Node(item);
            size++;
            return true;
        }

        if(c.compare(head.getItem(), item) > 0){
            Node tmp = head;
            Node first = new Node(item);

            head = first;
            head.setNext(tmp);
            tmp.setPrev(head);

            size++;
            return true;
        }

        if(head.getNext() == null){
            Node n = new Node(item);
            head.setNext(n);
            n.setPrev(head);

            size++;
            return true;
        }

        Node cursor = head;
        while (cursor != null) {

            if (cursor.next == null) {
                Node newNode = new Node(item, cursor, null);
                cursor.setNext(newNode);
                size++;
                return true;
            }

            if (c.compare(cursor.item, item) < 0 && c.compare(cursor.next.item, item) > 0) {
                Node newNode = new Node(item, cursor, cursor.next);
                cursor.setNext(newNode);
                cursor.next.setPrev(newNode);
                size++;
                return true;
            }

            cursor = cursor.next;
        }


        return false;
    }

//    Node tmp = head;
//    Node prevtmp = new Node(null);
//        while(tmp != null){
//        Node n = new Node(item, prevtmp, tmp);
//        if(tmp.next == null){
//            tmp.setNext(new Node(item, tmp, null));
//            size++;
//            return true;
//        }
//        if(c.compare(tmp.item, item) < 0 && c.compare(tmp.next.item, item) > 0) {
//            prevtmp.setNext(n);
//            tmp.setPrev(n);
//            size++;
//            return true;
//        }
//        prevtmp = tmp;
//        tmp = tmp.next;
//    }

    public Item remove(int index) {
        if (isEmpty())
            return null;

        if (size == 1) {
            if (index == 0) {
                Node tmp = head;
                head = null;
                size--;
                return tmp.getItem();
            }
        }

        if (index == 0) {
            Node tmp = head;
            head = head.getNext();
            size--;
            return tmp.getItem();
        }

        Node tmp = getNode(index);
        Node tmprev = tmp.getPrev();
        Node tmpnext = tmp.getNext();
        tmprev.setNext(tmpnext);
        size--;
        return tmp.getItem();
    }

    public Item getItem(int index){
        Node n = getNode(index);
        return n.getItem();
    }

    public int indexOf(Item item) {
        ListIterator<Item> it = this.listIterator();
        while(it.hasNext()){
            Item item2 = it.next();
            if(item2 == item)
                return it.nextIndex() - 1;
        }
        throw new NoSuchElementException();
    }

    public int indexOf(Node node) {
        Node tmp = head;
        int index = 0;

        while (tmp != null) {
            if (tmp == node)
                return index;
            tmp = tmp.next;
            index++;
        }
        throw new NoSuchElementException();
    }

    public boolean contains(Node node) {
        Node tmp = head;

        while (tmp != null) {
            if (tmp == node)
                return true;

            tmp = tmp.next;
        }
        return false;
    }

    public boolean contains(Item item) {
        ListIterator it = this.listIterator();
        while(it.hasNext()){
            if(it.next() == item)
                return true;
        }
        return false;
    }

    public double getTotalPrice() {
        double sum = 0;

        ListIterator<Item> it = this.listIterator();
        while(it.hasNext()){
            sum += it.next().getPrice();
        }

        return sum;
    }

    public ListIterator<Item> listIterator() {
        return new ItemIterator();

    }

    public ListIterator<Item> listIterator(int index) {
        ListIterator lit = new ItemIterator();
        ((ItemIterator) lit).setIndex(index);

        return lit;
    }

    static class Node{
        private Item item;
        private Node prev;
        private Node next;

        public Node(Item item){
            prev = null;
            this.item = item;
            next = null;
        }

        public Node(Item item, Node prev, Node next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }

        public Item getItem() {
            return item;
        }

        public void setItem(Item item) {
            this.item = item;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public Node getPrev() {
            return prev;
        }

        public void setPrev(Node prev) {
            this.prev = prev;
        }
    }

    class ItemIterator implements ListIterator {
        public Node current;//nodul curent
        public Node last; //ultimul nod accesat de metoda next() sau previous()
        public int index;

        public ItemIterator(){
            current = head;
            last = null;
            index = 0;
        }

        @Override
        public boolean hasNext(){ return index < size; }

        @Override
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();

            last = current;
            Item item = current.item;
            current = current.next;
            index++;

            return item;
        }

        @Override
        public boolean hasPrevious() {
            return index > 0;
        }

        public void setIndex(int index){
            this.index = index;
        }

        @Override
        public Item previous() {
            if (!hasPrevious()) throw new NoSuchElementException();
            current = current.prev;
            index--;
            last = current;
            return current.item;
        }

        @Override
        public int nextIndex() {
            return index;
        }

        @Override
        public int previousIndex() {
            return index - 1;
        }

        @Override
        public void remove() {
            if (last == null) throw new IllegalStateException();
            Node x = last.prev;
            Node y = last.next;
            x.next = y;
            y.prev = x;
            size--;

            if (current == last)
                current = y;
            else
                index--;

            last = null;
        }

        @Override
        public void set(Object o) {
            if (last == null) throw new IllegalStateException();
            last.item = (Item) o;
        }

        @Override
        public void add(Object o) {
            Node x = current.prev;
            Node y = new Node(null);
            Node z = current;
            y.item = (Item) o;
            x.next = y;
            y.next = z;
            z.prev = y;
            y.prev = x;
            size++;
            index++;
            last = null;
        }
    }


    }
