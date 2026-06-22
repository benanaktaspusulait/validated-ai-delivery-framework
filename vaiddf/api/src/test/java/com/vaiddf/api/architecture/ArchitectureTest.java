package com.vaiddf.api.architecture;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public class ArchitectureTest {

    private static final JavaClasses classes = new ClassFileImporter()
        .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
        .importPackages("com.vaiddf");

    @Test
    void core_should_not_depend_on_api() {
        ArchRule rule = noClasses()
            .that().resideInAPackage("..core..")
            .should().dependOnClassesThat()
            .resideInAPackage("..api..");
        rule.check(classes);
    }

    @Test
    void entities_should_reside_in_entity_package() {
        ArchRule rule = classes()
            .that().haveSimpleNameEndingWith("Entity")
            .should().resideInAPackage("..entity..");
        rule.check(classes);
    }
}
