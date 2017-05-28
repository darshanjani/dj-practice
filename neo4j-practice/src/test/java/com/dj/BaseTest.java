package com.dj;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
		CreateAgreements.class,
		CreateRelationships.class
})
public class BaseTest
{
}
