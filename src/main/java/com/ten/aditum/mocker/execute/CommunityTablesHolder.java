package com.ten.aditum.mocker.execute;

import com.ten.aditum.mocker.config.CommunityMeta;
import com.ten.aditum.mocker.config.CommunityMetaHolder;
import com.ten.aditum.mocker.strategy.AccessStrategyType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 社区信息表容器
 */
@Slf4j
@Service
public class CommunityTablesHolder {
    public static CommunityTablesHolder INSTANCE = new CommunityTablesHolder();
    /**
     * 默认采用General访问策略
     */
    private AccessStrategyType accessStrategyType = AccessStrategyType.GENERAL;

    private List<CommunityTable> communityTableList;

    /**
     * 初始化
     */
    private CommunityTablesHolder() {
        communityTableList = new ArrayList<>();

        // 获取Meta数据集合
        List<CommunityMeta> communityMetas = CommunityMetaHolder.getAllCommunityMeta();

        assert communityMetas.size() != 0;

        // 初始化Table容器
        communityMetas.forEach(communityMeta -> {
            CommunityTable table = new CommunityTable();
            table.setCommunityMeta(communityMeta);
            table.setAccessStrategyType(accessStrategyType);
            table.init();
            communityTableList.add(table);
        });
    }

    /**
     * 每个社区都执行一遍全设备随机访问
     */
    public void run() {
        communityTableList.forEach(CommunityTable::runAllRandomAccess);
    }

}
