FROM rabbitmq:3.12-management

LABEL authors="zaki<cnzakii@gmail.com>"

ARG RABBITMQ_DELAYED_MESSAGE_EXCHANGE=rabbitmq_delayed_message_exchange-3.12.0.ez

# 将插件复制进容器
COPY ${RABBITMQ_DELAYED_MESSAGE_EXCHANGE} /plugins

# 安装并启用插件
RUN rabbitmq-plugins enable --offline rabbitmq_delayed_message_exchange
