package pl.pwr;

import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(value = RetentionPolicy.RUNTIME)
public @interface DaneOsoboweField {
    String name();
}

