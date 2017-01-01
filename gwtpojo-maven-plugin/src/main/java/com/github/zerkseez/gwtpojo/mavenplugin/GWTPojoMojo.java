package com.github.zerkseez.gwtpojo.mavenplugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.reflections.Reflections;

import com.github.zerkseez.gwtpojo.annotation.GWTPojo;
import com.github.zerkseez.gwtpojo.codegen.GWTPojoClassInfo;
import com.github.zerkseez.gwtpojo.codegen.GWTPojoParserChain;
import com.github.zerkseez.gwtpojo.codegen.GWTPojoSerializerGenerator;
import com.github.zerkseez.gwtpojo.codegen.GWTPojoType;

@Mojo(name = "generate", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class GWTPojoMojo extends AbstractMojo {
    @Parameter(defaultValue = "${project}")
    private MavenProject project;

    @Parameter(defaultValue = "target/generated-sources/gwtpojo")
    private String outputDirectory;

    @Parameter
    private GWTPojoType[] pojoTypes;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        project.addCompileSourceRoot(outputDirectory);

        if (pojoTypes != null) {
            GWTPojoParserChain.INSTANCE.setPojoTypes(pojoTypes);
        }

        final Reflections reflections = new Reflections();
        final GWTPojoSerializerGenerator generator = new GWTPojoSerializerGenerator();
        for (Class<?> pojoClass : reflections.getTypesAnnotatedWith(GWTPojo.class)) {
            final GWTPojoClassInfo pojoInfo = GWTPojoParserChain.INSTANCE.parse(pojoClass);
            if (pojoInfo == null) {
                continue;
            }

            final String generatedSourceCode = generator.generate(pojoInfo);
            final File sourceDirectory = new File(project.getBasedir(), outputDirectory);
            final File packageDirectory = new File(sourceDirectory, pojoInfo.getPackageName().replaceAll("\\.", "/"));
            final File javaFile = new File(packageDirectory,
                    String.format("%s.java", pojoInfo.getSimpleSerializerClassName()));

            packageDirectory.mkdirs();

            try (OutputStream outputStream = new FileOutputStream(javaFile)) {
                outputStream.write(generatedSourceCode.getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                throw new MojoExecutionException("Failed writing generated source code", e);
            }
        }
    }
}
