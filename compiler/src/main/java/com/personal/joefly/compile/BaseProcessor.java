package com.personal.joefly.compile;

import com.squareup.javapoet.CodeBlock;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.code.Type;

import java.util.List;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.type.TypeMirror;

public abstract class BaseProcessor extends AbstractProcessor {
    protected Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
    }

    /**
     * 创建Interceptors。格式：<code>, new Interceptor1(), new Interceptor2()</code>
     */
    public CodeBlock buildInterceptors(List<? extends TypeMirror> interceptors) {
        CodeBlock.Builder b = CodeBlock.builder();
        if (interceptors != null && interceptors.size() > 0) {
            for (TypeMirror type : interceptors) {
                if (type instanceof Type.ClassType) {
                    Symbol.TypeSymbol e = ((Type.ClassType) type).asElement();
                    if(e instanceof Symbol.ClassSymbol) {
                        b.add(", new $T()", e);
                    }
                }
            }
        }
        return b.build();
    }
}
