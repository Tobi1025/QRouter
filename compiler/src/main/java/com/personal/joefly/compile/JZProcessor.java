package com.personal.joefly.compile;

import com.google.auto.service.AutoService;
import com.personal.joefly.interfaces.RouterUri;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypesException;
import javax.lang.model.type.TypeMirror;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class JZProcessor extends BaseProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        CodeBlock.Builder builder = CodeBlock.builder();
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(RouterUri.class);
        for (Element element : elements) {
            RouterUri uri = element.getAnnotation(RouterUri.class);
            CodeBlock interceptors = buildInterceptors(getInterceptors(uri));
            String [] pathList = uri.path();
            for (String path : pathList) {
                builder.addStatement("com.personal.joefly.qrouter.RouterBuilder.saveRouterClass($S,$T.class$L)", path, ClassName.get((TypeElement) element), interceptors);
            }
        }
        MethodSpec methodSpec = MethodSpec.methodBuilder("routerInit")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(TypeName.VOID)
                .addCode(builder.build())
                .build();

        TypeSpec uriAnnotationInit = TypeSpec.classBuilder("UriAnnotationInit")
                .addModifiers(Modifier.PUBLIC)
                .addMethod(methodSpec)
                .build();
        try {
            JavaFile.builder("com.personal.joefly.qrouter", uriAnnotationInit)
                    .build()
                    .writeTo(filer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return new HashSet<>(Collections.singletonList(RouterUri.class.getName()));
    }


    private static List<? extends TypeMirror> getInterceptors(RouterUri routerUri) {
        try {
            routerUri.interceptors();
        } catch (MirroredTypesException mte) {
            return mte.getTypeMirrors();
        }
        return null;
    }
}
