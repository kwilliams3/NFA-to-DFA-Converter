package com.example.kyle.nfatodfa.FiniteAutomata;

/**
 * Created by kyle on 1/4/16.
 */
final class MyStringBuilder {
    StringBuilder stringBuilder = new StringBuilder();

    void append(String string){
        stringBuilder.append(string);
        stringBuilder.append(" ");
    }

    void clear(){
        // Special thanks to anubhava for his answer on Stack Overflow regarding
        // whether it is better to reuse an existing StringBuilder or reallocate a new one.
        stringBuilder.setLength(0); // Set length of buffer to 0
        stringBuilder.trimToSize(); // Trim the underlying buffer
    }

    public String toString(){
        String string = stringBuilder.toString();
        return string.trim();
    }

    void replaceWith(String string){
        stringBuilder.replace(0, stringBuilder.length()-1, string);
    }
}
