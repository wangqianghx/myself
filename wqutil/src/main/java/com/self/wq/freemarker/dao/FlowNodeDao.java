package com.self.wq.freemarker.dao;

import com.self.wq.freemarker.domain.FlowNode;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface FlowNodeDao {

    List<FlowNode> findFlowNodeByFlowCode(String flowCode);

}
