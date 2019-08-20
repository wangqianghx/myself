package com.self.wq.freemarker.dao;

import com.self.wq.freemarker.domain.FlowManager;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface FlowManagerDao {

    List<FlowManager> findAllFlowManager();

}
