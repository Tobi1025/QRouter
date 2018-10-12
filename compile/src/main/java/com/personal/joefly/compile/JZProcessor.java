package com.personal.joefly.compile;

import com.google.auto.service.AutoService;
import com.personal.joefly.interfaces.Path;

import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
public class JZProcessor extends AbstractProcessor {

    private Messager messager;
    private Filer filer;

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        log("Test log in MyProcessor.process");
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(Path.class);
        for (Element element : elements) {
            String value = element.getAnnotation(Path.class).path();
            System.out.println("value = " + value);
        }
        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(Path.class.getCanonicalName());
//        return super.getSupportedAnnotationTypes();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        filer = processingEnv.getFiler();
    }

    /**
     * 打印日志
     * @param msg
     */
    private void log(String msg) {
        messager.printMessage(Diagnostic.Kind.ERROR, msg);
    }
}
