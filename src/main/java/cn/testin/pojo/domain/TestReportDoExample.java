package cn.testin.pojo.domain;

import java.util.ArrayList;
import java.util.List;

public class TestReportDoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TestReportDoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andProjectIdIsNull() {
            addCriterion("project_id is null");
            return (Criteria) this;
        }

        public Criteria andProjectIdIsNotNull() {
            addCriterion("project_id is not null");
            return (Criteria) this;
        }

        public Criteria andProjectIdEqualTo(String value) {
            addCriterion("project_id =", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdNotEqualTo(String value) {
            addCriterion("project_id <>", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdGreaterThan(String value) {
            addCriterion("project_id >", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdGreaterThanOrEqualTo(String value) {
            addCriterion("project_id >=", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdLessThan(String value) {
            addCriterion("project_id <", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdLessThanOrEqualTo(String value) {
            addCriterion("project_id <=", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdLike(String value) {
            addCriterion("project_id like", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdNotLike(String value) {
            addCriterion("project_id not like", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdIn(List<String> values) {
            addCriterion("project_id in", values, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdNotIn(List<String> values) {
            addCriterion("project_id not in", values, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdBetween(String value1, String value2) {
            addCriterion("project_id between", value1, value2, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdNotBetween(String value1, String value2) {
            addCriterion("project_id not between", value1, value2, "projectId");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("`name` is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("`name` is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("`name` =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("`name` <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("`name` >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("`name` >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("`name` <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("`name` <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("`name` like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("`name` not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("`name` in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("`name` not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("`name` between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("`name` not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("`type` is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("`type` is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("`type` =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("`type` <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("`type` >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("`type` >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("`type` <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("`type` <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("`type` in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("`type` not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("`type` between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("`type` not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andCaseTotalCountIsNull() {
            addCriterion("case_total_count is null");
            return (Criteria) this;
        }

        public Criteria andCaseTotalCountIsNotNull() {
            addCriterion("case_total_count is not null");
            return (Criteria) this;
        }

        public Criteria andCaseTotalCountEqualTo(Integer value) {
            addCriterion("case_total_count =", value, "caseTotalCount");
            return (Criteria) this;
        }

        public Criteria andCaseTotalCountNotEqualTo(Integer value) {
            addCriterion("case_total_count <>", value, "caseTotalCount");
            return (Criteria) this;
        }

        public Criteria andCaseTotalCountGreaterThan(Integer value) {
            addCriterion("case_total_count >", value, "caseTotalCount");
            return (Criteria) this;
        }

        public Criteria andCaseTotalCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("case_total_count >=", value, "caseTotalCount");
            return (Criteria) this;
        }

        public Criteria andCaseTotalCountLessThan(Integer value) {
            addCriterion("case_total_count <", value, "caseTotalCount");
            return (Criteria) this;
        }

        public Criteria andCaseTotalCountLessThanOrEqualTo(Integer value) {
            addCriterion("case_total_count <=", value, "caseTotalCount");
            return (Criteria) this;
        }

        public Criteria andCaseTotalCountIn(List<Integer> values) {
            addCriterion("case_total_count in", values, "caseTotalCount");
            return (Criteria) this;
        }

        public Criteria andCaseTotalCountNotIn(List<Integer> values) {
            addCriterion("case_total_count not in", values, "caseTotalCount");
            return (Criteria) this;
        }

        public Criteria andCaseTotalCountBetween(Integer value1, Integer value2) {
            addCriterion("case_total_count between", value1, value2, "caseTotalCount");
            return (Criteria) this;
        }

        public Criteria andCaseTotalCountNotBetween(Integer value1, Integer value2) {
            addCriterion("case_total_count not between", value1, value2, "caseTotalCount");
            return (Criteria) this;
        }

        public Criteria andCaseSuccessCountIsNull() {
            addCriterion("case_success_count is null");
            return (Criteria) this;
        }

        public Criteria andCaseSuccessCountIsNotNull() {
            addCriterion("case_success_count is not null");
            return (Criteria) this;
        }

        public Criteria andCaseSuccessCountEqualTo(Integer value) {
            addCriterion("case_success_count =", value, "caseSuccessCount");
            return (Criteria) this;
        }

        public Criteria andCaseSuccessCountNotEqualTo(Integer value) {
            addCriterion("case_success_count <>", value, "caseSuccessCount");
            return (Criteria) this;
        }

        public Criteria andCaseSuccessCountGreaterThan(Integer value) {
            addCriterion("case_success_count >", value, "caseSuccessCount");
            return (Criteria) this;
        }

        public Criteria andCaseSuccessCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("case_success_count >=", value, "caseSuccessCount");
            return (Criteria) this;
        }

        public Criteria andCaseSuccessCountLessThan(Integer value) {
            addCriterion("case_success_count <", value, "caseSuccessCount");
            return (Criteria) this;
        }

        public Criteria andCaseSuccessCountLessThanOrEqualTo(Integer value) {
            addCriterion("case_success_count <=", value, "caseSuccessCount");
            return (Criteria) this;
        }

        public Criteria andCaseSuccessCountIn(List<Integer> values) {
            addCriterion("case_success_count in", values, "caseSuccessCount");
            return (Criteria) this;
        }

        public Criteria andCaseSuccessCountNotIn(List<Integer> values) {
            addCriterion("case_success_count not in", values, "caseSuccessCount");
            return (Criteria) this;
        }

        public Criteria andCaseSuccessCountBetween(Integer value1, Integer value2) {
            addCriterion("case_success_count between", value1, value2, "caseSuccessCount");
            return (Criteria) this;
        }

        public Criteria andCaseSuccessCountNotBetween(Integer value1, Integer value2) {
            addCriterion("case_success_count not between", value1, value2, "caseSuccessCount");
            return (Criteria) this;
        }

        public Criteria andCaseFailCountIsNull() {
            addCriterion("case_fail_count is null");
            return (Criteria) this;
        }

        public Criteria andCaseFailCountIsNotNull() {
            addCriterion("case_fail_count is not null");
            return (Criteria) this;
        }

        public Criteria andCaseFailCountEqualTo(Integer value) {
            addCriterion("case_fail_count =", value, "caseFailCount");
            return (Criteria) this;
        }

        public Criteria andCaseFailCountNotEqualTo(Integer value) {
            addCriterion("case_fail_count <>", value, "caseFailCount");
            return (Criteria) this;
        }

        public Criteria andCaseFailCountGreaterThan(Integer value) {
            addCriterion("case_fail_count >", value, "caseFailCount");
            return (Criteria) this;
        }

        public Criteria andCaseFailCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("case_fail_count >=", value, "caseFailCount");
            return (Criteria) this;
        }

        public Criteria andCaseFailCountLessThan(Integer value) {
            addCriterion("case_fail_count <", value, "caseFailCount");
            return (Criteria) this;
        }

        public Criteria andCaseFailCountLessThanOrEqualTo(Integer value) {
            addCriterion("case_fail_count <=", value, "caseFailCount");
            return (Criteria) this;
        }

        public Criteria andCaseFailCountIn(List<Integer> values) {
            addCriterion("case_fail_count in", values, "caseFailCount");
            return (Criteria) this;
        }

        public Criteria andCaseFailCountNotIn(List<Integer> values) {
            addCriterion("case_fail_count not in", values, "caseFailCount");
            return (Criteria) this;
        }

        public Criteria andCaseFailCountBetween(Integer value1, Integer value2) {
            addCriterion("case_fail_count between", value1, value2, "caseFailCount");
            return (Criteria) this;
        }

        public Criteria andCaseFailCountNotBetween(Integer value1, Integer value2) {
            addCriterion("case_fail_count not between", value1, value2, "caseFailCount");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("`status` is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("`status` is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("`status` =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("`status` <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("`status` >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("`status` >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("`status` <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("`status` <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("`status` in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("`status` not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("`status` between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("`status` not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andTakeTimeIsNull() {
            addCriterion("take_time is null");
            return (Criteria) this;
        }

        public Criteria andTakeTimeIsNotNull() {
            addCriterion("take_time is not null");
            return (Criteria) this;
        }

        public Criteria andTakeTimeEqualTo(Long value) {
            addCriterion("take_time =", value, "takeTime");
            return (Criteria) this;
        }

        public Criteria andTakeTimeNotEqualTo(Long value) {
            addCriterion("take_time <>", value, "takeTime");
            return (Criteria) this;
        }

        public Criteria andTakeTimeGreaterThan(Long value) {
            addCriterion("take_time >", value, "takeTime");
            return (Criteria) this;
        }

        public Criteria andTakeTimeGreaterThanOrEqualTo(Long value) {
            addCriterion("take_time >=", value, "takeTime");
            return (Criteria) this;
        }

        public Criteria andTakeTimeLessThan(Long value) {
            addCriterion("take_time <", value, "takeTime");
            return (Criteria) this;
        }

        public Criteria andTakeTimeLessThanOrEqualTo(Long value) {
            addCriterion("take_time <=", value, "takeTime");
            return (Criteria) this;
        }

        public Criteria andTakeTimeIn(List<Long> values) {
            addCriterion("take_time in", values, "takeTime");
            return (Criteria) this;
        }

        public Criteria andTakeTimeNotIn(List<Long> values) {
            addCriterion("take_time not in", values, "takeTime");
            return (Criteria) this;
        }

        public Criteria andTakeTimeBetween(Long value1, Long value2) {
            addCriterion("take_time between", value1, value2, "takeTime");
            return (Criteria) this;
        }

        public Criteria andTakeTimeNotBetween(Long value1, Long value2) {
            addCriterion("take_time not between", value1, value2, "takeTime");
            return (Criteria) this;
        }

        public Criteria andPassRateIsNull() {
            addCriterion("pass_rate is null");
            return (Criteria) this;
        }

        public Criteria andPassRateIsNotNull() {
            addCriterion("pass_rate is not null");
            return (Criteria) this;
        }

        public Criteria andPassRateEqualTo(String value) {
            addCriterion("pass_rate =", value, "passRate");
            return (Criteria) this;
        }

        public Criteria andPassRateNotEqualTo(String value) {
            addCriterion("pass_rate <>", value, "passRate");
            return (Criteria) this;
        }

        public Criteria andPassRateGreaterThan(String value) {
            addCriterion("pass_rate >", value, "passRate");
            return (Criteria) this;
        }

        public Criteria andPassRateGreaterThanOrEqualTo(String value) {
            addCriterion("pass_rate >=", value, "passRate");
            return (Criteria) this;
        }

        public Criteria andPassRateLessThan(String value) {
            addCriterion("pass_rate <", value, "passRate");
            return (Criteria) this;
        }

        public Criteria andPassRateLessThanOrEqualTo(String value) {
            addCriterion("pass_rate <=", value, "passRate");
            return (Criteria) this;
        }

        public Criteria andPassRateLike(String value) {
            addCriterion("pass_rate like", value, "passRate");
            return (Criteria) this;
        }

        public Criteria andPassRateNotLike(String value) {
            addCriterion("pass_rate not like", value, "passRate");
            return (Criteria) this;
        }

        public Criteria andPassRateIn(List<String> values) {
            addCriterion("pass_rate in", values, "passRate");
            return (Criteria) this;
        }

        public Criteria andPassRateNotIn(List<String> values) {
            addCriterion("pass_rate not in", values, "passRate");
            return (Criteria) this;
        }

        public Criteria andPassRateBetween(String value1, String value2) {
            addCriterion("pass_rate between", value1, value2, "passRate");
            return (Criteria) this;
        }

        public Criteria andPassRateNotBetween(String value1, String value2) {
            addCriterion("pass_rate not between", value1, value2, "passRate");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Long value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Long value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Long value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Long value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Long value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Long value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Long> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Long> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Long value1, Long value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Long value1, Long value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Long value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Long value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Long value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Long value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Long value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Long value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Long> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Long> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Long value1, Long value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Long value1, Long value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}