/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * 
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.contrib.qaframework.automation;
 
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.openmrs.contrib.qaframework.RunTest;
import org.openmrs.contrib.qaframework.page.ConfigureMetadataPage;
import org.openmrs.contrib.qaframework.page.OpenConceptLabPage;
import org.openmrs.contrib.qaframework.page.OpenConceptLabSuccessPage;
import org.openmrs.contrib.qaframework.page.SubscriptionPage;
 
public class OpenConceptLabSteps extends Steps {
	
    private OpenConceptLabPage openconceptlabpage;
    private ConfigureMetadataPage configureMetadataPage;
    private static final String RELEASE_DICTIONARY_URL = "https://api.staging.openconceptlab.org/users/openmrs/collections/TD-ecly/65725685/";
    private static final String TOKEN = "bd022mad6d3df24f5c42ewewa94b53a23edf6eee7r";
    private static final String EDITED_TOKEN = "bd022mad6d3df24f5c42ewewa94b53a23edf6fff8r";
    private OpenConceptLabSuccessPage openConceptLabSuccessPage;
    private SubscriptionPage subscriptionPage;
    
    @Before(RunTest.HOOK.SELENIUM_OPEN_CONCEPT_LAB)
    public void visitHomePage() {
        initiateWithLogin();    
    }
    
    @After(RunTest.HOOK.SELENIUM_OPEN_CONCEPT_LAB)
    public void destroy() {
        quit();
    }
    
    @Given("a user clicks on configure Metadata link from home page")
    public void launchMetadataDashboard() {
    	configureMetadataPage = (ConfigureMetadataPage) homePage.goToConfigureMetadata();
    }
    
    @When("the system loads the configure metadata page")
    public void systemLoadsConfigureMetadataPage() {
    	assertTrue(textExists("Manage OCL"));
    }
 
    @And("a user clicks Manage OCL link from configure metadata page")
    public void loadOpenConceptLabPage() {
        openconceptlabpage = (OpenConceptLabPage) configureMetadataPage.goToOpenConceptLabPage();
    }
    
    @Then("the system loads Open Concept Lab page")
    public void systemLoadsOpenConceptPage() {
    	assertTrue(textExists("Setup Subscription"));
    }
    
    @When("a user clicks on Setup subscription button")
    public void loadSubscriptionPage() {
      subscriptionPage = (SubscriptionPage) openconceptlabpage.clickOnsetupSubscriptionButton();
    }
 
    @Then("the system loads Subscription page")
    public void systemLoadsSubscriptionPage() {
        assertTrue(textExists("Show/hide advanced..."));
    }

    @And("a user enters the URL of a new released dictionary")
    public void enterDictionaryUrl() {
        subscriptionPage.enterSubscriptionURL(RELEASE_DICTIONARY_URL);
    }   

    @And("a user enters the Token")
    public void enterToken() {
         subscriptionPage.enterToken(TOKEN);
    }

    @And ("a user clicks on the Save Changes button")
    public void saveChanges() {
        openConceptLabSuccessPage = (OpenConceptLabSuccessPage) subscriptionPage.clickSaveChangesButton();
    }

    @Then("the system loads Open Concept Lab Success page")
    public void systemLoadsOpenConceptLabSuccessPage() {
    	 assertTrue(textExists("Import from subscription server"));  
    }

    @And("a user clicks import from Subscription server button")
    public void LoadsOpenConceptLabPage() {
        openConceptLabSuccessPage.clickOnImportFromSubscriptionButton();
        openConceptLabSuccessPage.waitForPageToLoad();
    }

    @Then("the API should be displayed on the previous imports")
    public void displayPreviousImports() {
         assertNotNull(openConceptLabSuccessPage.getpreviousImportsList());
    } 

    @When("a user clicks edit subscription button")
    public void loadsSubscriptionPage() {
         subscriptionPage = (SubscriptionPage) openConceptLabSuccessPage.clickEditSubscriptionButton();  
     } 
    
    @And("a user edits the URL of a released dictionary")
    public void editDictionaryUrl() {
        subscriptionPage.enterSubscriptionURL("https://api.qa-refapp.openmrs.org/openmrs/dictionary/concept.htm?conceptId=162172");
    }   

    @And("a user edits the Token")
    public void editToken() {
        subscriptionPage.enterToken(EDITED_TOKEN);
    } 
    
    @And("a user clicks unsubscribe button")
    public void getSubscriptionPage() {
       subscriptionPage.clickOnUnSubscribeButton();  
    } 
}
