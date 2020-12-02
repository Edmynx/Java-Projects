public class SubClass extends SuperClass {
    public void doIt() {
        super.doIt();
        System.out.println("Sub’s doIt");
    }
    public void doItMore() {
        System.out.println("Sub’s doItMore");
        super.doItMore();
    }
    public void doItSub() {
        System.out.println("doItSub");
    }
}
