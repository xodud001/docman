package api.doc.docman.annotation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class AnnotationTest {
    @DisplayName("1. Test Custom Annotation")
    @Test
    void test1() throws NoSuchMethodException{
        Method method = TheClass.class.getMethod("doThis");
        Annotation[] annotations = method.getDeclaredAnnotations();

        for(Annotation annotation : annotations){
            if(annotation instanceof MyAnnotation){
                MyAnnotation myAnnotation = (MyAnnotation) annotation;
                System.out.println("name : " + myAnnotation.name());
                System.out.println("value : " + myAnnotation.value());
            }
        }
    }

    @DisplayName("2. Test Custom Annotation")
    @Test
    void test2() throws NoSuchMethodException{
        Annotation annotation = TheClass.class.getMethod("doThat").getAnnotation(MyAnnotation.class);

        if(annotation instanceof MyAnnotation){
            MyAnnotation myAnnotation = (MyAnnotation) annotation;
            System.out.println("name : " + myAnnotation.name());
            System.out.println("value : " + myAnnotation.value());
        }
    }
}
