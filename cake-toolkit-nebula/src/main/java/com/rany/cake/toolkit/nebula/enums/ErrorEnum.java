package com.rany.cake.toolkit.nebula.enums;


import com.rany.cake.toolkit.nebula.common.ResponseService;

public enum ErrorEnum implements ResponseService {

    /**
     * 异常枚举
     */
    EMPTY_GRAPH_ADDRESS("graph_001", "Empty graph addresses"),
    EMPTY_SPACE_NAME("graph_002", "Empty space name"),
    SESSION_COUNT_REACH_LIMIT("graph_003", "The SessionsManager does not have available sessions."),
    CONN_POOL_INIT_FAIL("graph_004", "Init pool failed: services are broken."),
    SESSION_MANAGER_CLOSED("graph_005", "The SessionsManager was closed."),
    FAIL_TO_SWITCH_SPACE("graph_006", "Switch space failed."),
    OPERATION_FAILED("graph_007", "Operation failed."),
    NGQL_EXEC_FAILED("graph_008", "Ngql execute failed"),
    ;

    private final String code;
    private final String desc;

    ErrorEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public String getResponseCode() {
        return this.code;
    }

    @Override
    public String getResponseMessage() {
        return this.desc;
    }
}
