package com.self.wq.freemarker.dao;

import com.self.wq.freemarker.domain.TableCol;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TableColDao {

    List<TableCol> findAllTableCol();

}
