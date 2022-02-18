import java.util.Scanner;

public class CaeserChiffre {

    private static Scanner scan = new Scanner(System.in);

    public void verschluesseln()
    {
        System.out.println("Bitte geben Sie einen Text ein:");
        String input = scan.nextLine();
        scan.close();
        
        System.out.println(input + input.length());
        String output = verschlüsseleInput(input);
        System.out.println(output);
    }

    // ausgelagert
    private String verschlüsseleInput(String input) {
        String output = "";
        char zeichen;
        boolean groß;
        for(int i = 0; i<input.length();i++)
        {
            zeichen = input.charAt(i);
            if(zeichen < 91)
                groß = false;
            else
                groß = true;
            zeichen += 13;
            if(zeichen>122)
                zeichen -= 26;
            else if(zeichen > 90 && !groß)
                zeichen -= 26;
            else if(zeichen < 65)
                zeichen = 32;
            output += zeichen;
        }
        return output;
    }

    public void verschluesseln(int k)
    {
        if(k > 26)
            k = 13;
        else if(k<=0)
            k = 13;
        System.out.println("Bitte geben Sie einen Text ein:");
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        String output = "";
        char zeichen = 0;
        boolean groß = false;
        for(int i = 0; i<input.length();i++)
        {
            zeichen = input.charAt(i);
            if(zeichen < 91)
                groß = false;
            else
                groß = true;
            zeichen += k;
            if(zeichen>122)
                zeichen -= 26;
            else if(zeichen > 90 && !groß)
                zeichen -= 26;
            else if(zeichen < 65)
                zeichen = 32;
            output += zeichen;
        }
        System.out.println(output);
        scan.close();
    }
}
