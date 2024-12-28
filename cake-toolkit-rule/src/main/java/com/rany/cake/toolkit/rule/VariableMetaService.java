package com.rany.cake.toolkit.rule;

import java.util.List;
import java.util.Map;

public interface VariableMetaService {


    /**
     * 由业务自己实现，返回变量的CODE和NAME
     *
     * @return
     */
    List<VariableDesc> listVariableCodeName(Map<String, Object> qryParams);


    VariableRenderInfo getRenderInfo(String code, String secondCode);


    List<Option> getVariableParamValue(String code, String secondCode, String paramName, Map<String, String> extParam);


    List<Option> getRightOptions(String code, String secondCode);


}
