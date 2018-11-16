public class LinkedListDeque<Type> implements  Deque<Type>{
    /* private helper method for LinkedListDeque */
    private class ListNode {

        private Type item; // why private?
        private ListNode pre; //it is different form SLList as the project required
        private ListNode next;

        private ListNode(Type i, ListNode p, ListNode n) {
            item = i;
            pre = p;
            next = n;
        }
    }

    /* Declare fields */
    private ListNode sentinel;
    private int size;

    /* Creates an empty linked list deque. */
    public LinkedListDeque() {
        sentinel = new ListNode(null, null, null);
        sentinel.next = sentinel;
        sentinel.pre = sentinel;
    }

    @Override
    public void addFirst(Type item) {
        /* we have to pay attention here that the node is not connected with each other,
         * they are pointing to the item instead of the next node!
         */
        sentinel.next = new ListNode(item, sentinel, sentinel.next);
        sentinel.next.next.pre = sentinel.next; //画图就明白了,就是指针方向变化的问题
        size += 1;
    }

    @Override
    public void addLast(Type item) {
        sentinel.pre = new ListNode(item, sentinel.pre, sentinel);
        sentinel.pre.pre.next = sentinel.pre;
        size += 1;
    }

    @Override
    public boolean isEmpty() {
        return (size ==  0); // What if I do not use this. here? --> same as this.size!
    }

    @Override
    public int size() {
        return size;
    }

    /*  Prints the items in the deque from first to last, separated by a space. */
    @Override
    public void printDeque() {
        /* It is okay if we use ListNode p = sentinel or this one 只是多了个遥控器，指向的其实是同一个东西 */
        ListNode p = new ListNode(null, null, sentinel.next); // Just a test here ! It works!
        /* 因为这是一个环形的list，所以当头遇到尾巴的时候loop结束 */
        while (p.next != sentinel) {
            System.out.print(p.next.item + " ");
            p.next = p.next.next; //Linked List 的标准操作
        }
    }

    /* 下面两个method的原理相同，都是改变了一下指针指向的位置，把后面一个赋给了前面一个 */
    @Override
    public Type removeFirst() {
        if (size == 0) {
            return null;
        }
        Type item = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.pre = sentinel;
        size -= 1;
        return item;
    }

    @Override
    public Type removeLast() {
        if (size == 0) {
            return null;
        }
        Type item = sentinel.pre.item;
        sentinel.pre = sentinel.pre.pre;
        sentinel.pre.next = sentinel;
        size -= 1;
        return item;
    }

    /* get the item of a node with the specific index. */
    public Type getIterative(int index) {
        ListNode p = sentinel.next; // think about the image of a box and a  address！
        while (p != sentinel) {
            if(index == 0) {
                return p.item;
            }
            index--;
            p = p.next;
        }
        /* if it reach the end but nothing found return null */
        return null;
    }

    /* 因为只给了一个index，参数里面没有ListNode，无法recursion，所以只能用helper method来实现*/
    public Type getRecursive(int index) {
        if (index > size - 1) {
            return null;
        } else {
            return traverse(sentinel.next, index);
        }
    }

    /* helper method of getRecursive(int index) to include ListNode 类比之前课上的example --> size() */
    private Type traverse(ListNode n, int i) {
        if (i == 0) {
            return n.item;
        }
        return traverse(n.next, --i); // test if i - 1 is the same as --i --> Yes, they are the same
    }
}
