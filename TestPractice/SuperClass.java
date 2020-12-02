
public class SuperClass {
    public void doIt() {
        doItMore();
        System.out.println("Super’s doIt");
    }
    public void doItMore() {
        System.out.println("Super’s doItMore");

    }
    public void doItSuper() {
        System.out.println("doItSuper");
    }

    public static void main(String[] args) {
        SuperClass superObj = new SuperClass();
        SuperClass superObj2 = new SubClass();
        SubClass subObj = new SubClass();

        superObj.doItSuper();
        System.out.println(" ");



        superObj2.doItSuper();
        System.out.println(" ");


        subObj.doItSuper();
        System.out.println(" ");

        subObj.doItSub();
        System.out.println(" ");


        superObj.doIt();
        System.out.println(" ");

        superObj2.doItMore();
        System.out.println(" ");

        subObj.doIt();
        System.out.println(" ");

    }

}