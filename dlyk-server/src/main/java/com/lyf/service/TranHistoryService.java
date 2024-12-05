package com.lyf.service;



import com.lyf.model.TTranHistory;
import com.lyf.query.TranHistoryQuery;

import java.util.List;

public interface TranHistoryService {

    int saveTranHistory(TranHistoryQuery tranHistoryQuery);

    List<TTranHistory> getTranHistoryByTranId(Integer tranId);
}
