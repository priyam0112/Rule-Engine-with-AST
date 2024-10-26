package com.ruleengine.Rule.Engine.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ruleengine.Rule.Engine.model.Rule;

public interface RuleRepository extends MongoRepository<Rule, String> {
}
