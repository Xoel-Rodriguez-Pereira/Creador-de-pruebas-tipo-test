package org.autotest.model;

import java.io.IOException;

public enum Solucion {
  A, B, C, D;

        public String toValue() {
            switch (this) {
            case A -> {return "A";}
            case B -> {return "B";}
            case C -> { return "C";}
            case D -> {return "D";}
            }
            return null;
        }

        public static Solucion forValue(String value) throws IOException {
            if (value.equals("A")) return A;
            if (value.equals("B")) return B;
            if (value.equals("C")) return C;
            if (value.equals("D")) return D;
            throw new IOException("Cannot deserialize Solucion");
        }
    }