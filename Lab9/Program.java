import java.nio.file.Paths;
import java.util.Scanner;


public class Program {
    static {
        try {
            String dotProductPath = Paths.get(Paths.get(".").toAbsolutePath().toString(), "DotProduct.so").toAbsolutePath().toString();

            System.load(dotProductPath);
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Nie udało się załadować biblioteki natywnej: " + e);
            System.exit(1);
        }
    }

    static Scanner in = new Scanner(System.in);


    private static void l(String str){
        System.out.println(str);
    }

    public static void ShowAvailableOptions(){
        l("1 - Multi01 - pomnozenie dwoch wektorow");
        l("2 - Multi02 - iloczyn skalarny tego samego wektora");
        l("3 - Multi03 - cpp konsola wszystko przejmuje");
        l("4 - exit");
        l("Twoj wybor?");
    }


    private static Double[] ReadVector(int len, String vector_name){
        l("Podaj wektor " + vector_name);
        Double[] r = new Double[len];

        for(int i = 0; i < len; i++)
            r[i] = new Double(in.nextDouble());


        return r;
    }

    public static void ShowMenu(){

        Double[] a, b;
        DotProduct p;
        int v_len;
        boolean exit = false;

        while(!exit){
            ShowAvailableOptions();

            int option = in.nextInt();
            switch (option){
                case 1:
                    l("Podaj dlugosc wektorow");
                    v_len = in.nextInt();

                    a = ReadVector(v_len, "A");
                    b = ReadVector(v_len, "B");

                    p = new DotProduct();
                    l(p.multi01(a, b).toString());
                    break;
                case 2:
                    l("Podaj dlugosc wektora");
                    v_len = in.nextInt();

                    a = ReadVector(v_len, "A");

                    p = new DotProduct();
                    p.setB(a);
                    l(p.multi02(a).toString());
                    break;
                case 3:
                    p = new DotProduct();

                    p.multi03();

                    l(Double.toString(p.getC()));
                    break;
                
                case 4:
                    exit = true;
                    break;
            }
        }
    }

    public static void main(String[] args) {
        ShowMenu();

        // DotProduct p = new DotProduct();

        // Double[] a = new Double[] {
        //     new Double(1), new Double(2), new Double(3)
        // };
        // Double[] b = new Double[] {
        //     new Double(1), new Double(2), new Double(3)
        // };

        // p.setA(a);
        // p.setB(b);

        // Double r_01 = p.multi01(a, b);
        // Double r_02 = p.multi02(a);

        // System.out.println(r_01);
        // System.out.println(r_02);




        // DotProduct p_2 = new DotProduct();
        // p_2.multi03();
        // System.out.print(p_2.getC());
    }
}