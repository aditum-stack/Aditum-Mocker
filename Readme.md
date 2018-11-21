# Aditum Mocker

> Log Mocker server for Aditum. Aditum模拟日志生成服务

-------------------------------------------------------------------------------

## 微服务功能

模拟生成门禁访问记录，通过Quartz定时调度产生日志文件，模拟数据由Mocker数据表预设提供

## 技术栈

Springboot + slf4j + Springcloud微服务架构

## 微服务配置

微服务配置

    name : mocker
    port : 9001

Cloud配置

    eureka port : 10001
    config port : 10002

## 启动

启动Application

1. 从Config配置中心获取数据库配置信息和日志配置信息
2. 从数据库中获取Mocker信息，并由此生产模拟日志
3. 不断产生流日志，被collector微服务获取