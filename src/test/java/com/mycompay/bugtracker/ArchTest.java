package com.mycompay.bugtracker;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.mycompay.bugtracker");

        noClasses()
            .that()
            .resideInAnyPackage("com.mycompay.bugtracker.service..")
            .or()
            .resideInAnyPackage("com.mycompay.bugtracker.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.mycompay.bugtracker.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
