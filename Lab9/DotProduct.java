
public class DotProduct {
    private Double[] a;
    private Double[] b;
    private Double c;

    public Double[] getA(){ return a; }
    public Double[] getB(){ return b; }
    public double getC(){ return c; }

    public void setA(Double[] a){ this.a = a; }
    public void setB(Double[] b){ this.b = b; }
    public void setC(double c){ this.c = c; }

    /*
    zakładamy, że po stronie kodu natywnego wyliczony zostanie iloczyn skalarny dwóch wektorów
     */
    public native Double multi01(Double[] a, Double[] b);

    /*
    zakładamy, że drugi atrybut będzie pobrany z obiektu przekazanego do metody natywnej
     */
    public native Double multi02(Double[] a);

    /*
    zakładamy, że po stronie natywnej utworzone zostanie okienko na atrybuty,
    a po ich wczytaniu i przepisaniu do a,b obliczony zostanie wynik.
    Wynik powinna wyliczać metoda Javy multi04
    (korzystająca z parametrów a,b i wpisująca wynik do c).
     */
    public native void multi03();

    private void multi04(){

        // mnoży a i b, wynik wpisuje do c
        double r = multi01(getA(), getB());
        setC(r);
    }
}