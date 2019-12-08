public class Test {
//    Test() {
//
//    }
    public class tt {
        int i = 0;
        void print() {
            System.out.println(i);

        }
    }

    public static void main(String[] args) {
        Test t = new son();

    }
}

class son extends Test {
    son() {
        tt x = new tt();
        x.print();
    }
    public class tt{
        int i = 8;
        void print () {
            System.out.println(i);
        }

    }
}


