package com.personal.joefly.compile;

import com.google.auto.service.AutoService;
import com.personal.joefly.interfaces.Path;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
//import com.sun.tools.javac.code.Symbol;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class JZProcessor extends AbstractProcessor {

    private Filer filer;

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        CodeBlock.Builder builder = CodeBlock.builder();
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(Path.class);
        for (Element element : elements) {
            String path = element.getAnnotation(Path.class).path();
            builder.addStatement("com.personal.joefly.qrouter.RouterBuilder.route($S,$T.class)", path, ClassName.get((TypeElement) element));
        }
        MethodSpec methodSpec = MethodSpec.methodBuilder("routebuild")
                .addModifiers(Modifier.PUBLIC)
                .returns(TypeName.VOID)
                .addCode(builder.build())
                .build();

        TypeSpec hello = TypeSpec.classBuilder("JumpMapping")
                .addModifiers(Modifier.PUBLIC)
                .addMethod(methodSpec)
                .build();
        try {
            JavaFile.builder("com.personal.joefly.qrouter", hello)
                    .build()
                    .writeTo(filer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return new HashSet<>(Collections.singletonList(Path.class.getName()));
    }


    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
    }

}
