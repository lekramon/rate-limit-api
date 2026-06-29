# language: pt

Funcionalidade: Reserva de limite por processo

  Cenários temporariamente comentados até implementarmos os steps e a camada de aplicação.
#
#   Como uma API que posta transações
#   Quero consultar o Rate Limit antes de postar uma transação
#   Para garantir que o processo não ultrapasse os limites configurados
#
#   Contexto:
#     Dado que exista a configuração de rate limit para a chave "rateLimit.parce-compras"
#
#     E existam as seguintes janelas de limite para o processType "parce-compras":
#       | tipo    | periodo    | limite    | disponivel | reservado | consumido |
#       | DAILY   | 2026-06-28 | 10000,00  | 10000,00   | 0,00      | 0,00      |
#       | WEEKLY  | 2026-W26   | 50000,00  | 50000,00   | 0,00      | 0,00      |
#       | MONTHLY | 2026-06    | 100000,00 | 100000,00  | 0,00      | 0,00      |
#
#   Cenário: Processo com limite diário, semanal e mensal disponível pode reservar limite
#     Quando a API solicitar reserva no valor de 2400,68 para o processType "parce-compras"
#     Então a postagem deve ser permitida
#     E uma reserva pendente deve ser criada
#     E as janelas de limite devem ficar assim:
#       | tipo    | limite    | disponivel | reservado | consumido |
#       | DAILY   | 10000,00  | 7599,32    | 2400,68   | 0,00      |
#       | WEEKLY  | 50000,00  | 47599,32   | 2400,68   | 0,00      |
#       | MONTHLY | 100000,00 | 97599,32   | 2400,68   | 0,00      |
#
#   Cenário: Processo com limite diário excedido não pode reservar limite
#     Dado que existam as seguintes janelas de limite para o processType "parce-compras":
#       | tipo    | periodo    | limite    | disponivel | reservado | consumido |
#       | DAILY   | 2026-06-28 | 10000,00  | 2000,00    | 8000,00   | 0,00      |
#       | WEEKLY  | 2026-W26   | 50000,00  | 50000,00   | 0,00      | 0,00      |
#       | MONTHLY | 2026-06    | 100000,00 | 100000,00  | 0,00      | 0,00      |
#     Quando a API solicitar reserva no valor de 2400,68 para o processType "parce-compras"
#     Então a postagem deve ser recusada
#     E nenhuma reserva deve ser criada
#     E o motivo da decisão deve ser "DAILY_LIMIT_EXCEEDED"
#     E as janelas de limite devem ficar assim:
#       | tipo    | limite    | disponivel | reservado | consumido |
#       | DAILY   | 10000,00  | 2000,00    | 8000,00   | 0,00      |
#       | WEEKLY  | 50000,00  | 50000,00   | 0,00      | 0,00      |
#       | MONTHLY | 100000,00 | 100000,00  | 0,00      | 0,00      |
#
#   Cenário: Processo com limite semanal excedido não pode reservar limite
#     Dado que existam as seguintes janelas de limite para o processType "parce-compras":
#       | tipo    | periodo    | limite    | disponivel | reservado | consumido |
#       | DAILY   | 2026-06-28 | 10000,00  | 10000,00   | 0,00      | 0,00      |
#       | WEEKLY  | 2026-W26   | 50000,00  | 2000,00    | 48000,00  | 0,00      |
#       | MONTHLY | 2026-06    | 100000,00 | 100000,00  | 0,00      | 0,00      |
#     Quando a API solicitar reserva no valor de 2400,68 para o processType "parce-compras"
#     Então a postagem deve ser recusada
#     E nenhuma reserva deve ser criada
#     E o motivo da decisão deve ser "WEEKLY_LIMIT_EXCEEDED"
#     E as janelas de limite devem ficar assim:
#       | tipo    | limite    | disponivel | reservado | consumido |
#       | DAILY   | 10000,00  | 10000,00   | 0,00      | 0,00      |
#       | WEEKLY  | 50000,00  | 2000,00    | 48000,00  | 0,00      |
#       | MONTHLY | 100000,00 | 100000,00  | 0,00      | 0,00      |
#
#   Cenário: Processo com limite mensal excedido não pode reservar limite
#     Dado que existam as seguintes janelas de limite para o processType "parce-compras":
#       | tipo    | periodo    | limite    | disponivel | reservado | consumido |
#       | DAILY   | 2026-06-28 | 10000,00  | 10000,00   | 0,00      | 0,00      |
#       | WEEKLY  | 2026-W26   | 50000,00  | 50000,00   | 0,00      | 0,00      |
#       | MONTHLY | 2026-06    | 100000,00 | 2000,00    | 98000,00  | 0,00      |
#     Quando a API solicitar reserva no valor de 2400,68 para o processType "parce-compras"
#     Então a postagem deve ser recusada
#     E nenhuma reserva deve ser criada
#     E o motivo da decisão deve ser "MONTHLY_LIMIT_EXCEEDED"
#     E as janelas de limite devem ficar assim:
#       | tipo    | limite    | disponivel | reservado | consumido |
#       | DAILY   | 10000,00  | 10000,00   | 0,00      | 0,00      |
#       | WEEKLY  | 50000,00  | 50000,00   | 0,00      | 0,00      |
#       | MONTHLY | 100000,00 | 2000,00    | 98000,00  | 0,00      |
#
#   Cenário: Processo sem rate limit habilitado pode postar sem reserva
#     Dado que exista a configuração de rate limit para a chave "rateLimit.parce-compras-disabled"
#     Quando a API solicitar reserva no valor de 2400,68 para o processType "parce-compras-disabled"
#     Então a postagem deve ser permitida
#     E nenhuma reserva deve ser criada
#     E o motivo da decisão deve ser "RATE_LIMIT_DISABLED"
#
#   Cenário: Processo com apenas limite mensal disponível pode reservar limite
#     Dado que exista a configuração de rate limit para a chave "rateLimit.parce-compras-only-monthly"
#
#     E existam as seguintes janelas de limite para o processType "parce-compras-only-monthly":
#       | tipo    | periodo | limite    | disponivel | reservado | consumido |
#       | MONTHLY | 2026-06 | 100000,00 | 100000,00  | 0,00      | 0,00      |
#
#     Quando a API solicitar reserva no valor de 2400,68 para o processType "parce-compras-only-monthly"
#     Então a postagem deve ser permitida
#     E uma reserva pendente deve ser criada
#     E somente a janela mensal deve ser reservada
#     E as janelas de limite devem ficar assim:
#       | tipo    | limite    | disponivel | reservado | consumido |
#       | MONTHLY | 100000,00 | 97599,32   | 2400,68   | 0,00      |
#
#   Cenário: Processo com limite diário e mensal disponível pode reservar limite
#     Dado que exista a configuração de rate limit para a chave "rateLimit.parce-compras-daily-monthly"
#
#     E existam as seguintes janelas de limite para o processType "parce-compras-daily-monthly":
#       | tipo    | periodo    | limite    | disponivel | reservado | consumido |
#       | DAILY   | 2026-06-28 | 10000,00  | 10000,00   | 0,00      | 0,00      |
#       | MONTHLY | 2026-06    | 100000,00 | 100000,00  | 0,00      | 0,00      |
#
#     Quando a API solicitar reserva no valor de 2400,68 para o processType "parce-compras-daily-monthly"
#     Então a postagem deve ser permitida
#     E uma reserva pendente deve ser criada
#     E somente as janelas diária e mensal devem ser reservadas
#     E as janelas de limite devem ficar assim:
#       | tipo    | limite    | disponivel | reservado | consumido |
#       | DAILY   | 10000,00  | 7599,32    | 2400,68   | 0,00      |
#       | MONTHLY | 100000,00 | 97599,32   | 2400,68   | 0,00      |
#
#   Cenário: Reserva confirmada vira consumo efetivo
#     Dado que exista uma reserva pendente no valor de 2400,68 para o processType "parce-compras"
#
#     E existam as seguintes janelas de limite para o processType "parce-compras":
#       | tipo    | periodo    | limite    | disponivel | reservado | consumido |
#       | DAILY   | 2026-06-28 | 10000,00  | 7599,32    | 2400,68   | 0,00      |
#       | WEEKLY  | 2026-W26   | 50000,00  | 47599,32   | 2400,68   | 0,00      |
#       | MONTHLY | 2026-06    | 100000,00 | 97599,32   | 2400,68   | 0,00      |
#
#     Quando a reserva for confirmada
#     Então a reserva deve ficar com status "CONFIRMED"
#     E as janelas de limite devem ficar assim:
#       | tipo    | limite    | disponivel | reservado | consumido |
#       | DAILY   | 10000,00  | 7599,32    | 0,00      | 2400,68   |
#       | WEEKLY  | 50000,00  | 47599,32   | 0,00      | 2400,68   |
#       | MONTHLY | 100000,00 | 97599,32   | 0,00      | 2400,68   |
#
#   Cenário: Reserva cancelada devolve limite disponível
#     Dado que exista uma reserva pendente no valor de 2400,68 para o processType "parce-compras"
#
#     E existam as seguintes janelas de limite para o processType "parce-compras":
#       | tipo    | periodo    | limite    | disponivel | reservado | consumido |
#       | DAILY   | 2026-06-28 | 10000,00  | 7599,32    | 2400,68   | 0,00      |
#       | WEEKLY  | 2026-W26   | 50000,00  | 47599,32   | 2400,68   | 0,00      |
#       | MONTHLY | 2026-06    | 100000,00 | 97599,32   | 2400,68   | 0,00      |
#
#     Quando a reserva for cancelada
#     Então a reserva deve ficar com status "CANCELED"
#     E as janelas de limite devem ficar assim:
#       | tipo    | limite    | disponivel | reservado | consumido |
#       | DAILY   | 10000,00  | 10000,00   | 0,00      | 0,00      |
#       | WEEKLY  | 50000,00  | 50000,00   | 0,00      | 0,00      |
#       | MONTHLY | 100000,00 | 100000,00  | 0,00      | 0,00      |
#
#   Cenário: Reserva expirada devolve limite disponível
#     Dado que exista uma reserva pendente vencida no valor de 2400,68 para o processType "parce-compras"
#
#     E existam as seguintes janelas de limite para o processType "parce-compras":
#       | tipo    | periodo    | limite    | disponivel | reservado | consumido |
#       | DAILY   | 2026-06-28 | 10000,00  | 7599,32    | 2400,68   | 0,00      |
#       | WEEKLY  | 2026-W26   | 50000,00  | 47599,32   | 2400,68   | 0,00      |
#       | MONTHLY | 2026-06    | 100000,00 | 97599,32   | 2400,68   | 0,00      |
#
#     Quando o worker expirar a reserva
#     Então a reserva deve ficar com status "EXPIRED"
#     E as janelas de limite devem ficar assim:
#       | tipo    | limite    | disponivel | reservado | consumido |
#       | DAILY   | 10000,00  | 10000,00   | 0,00      | 0,00      |
#       | WEEKLY  | 50000,00  | 50000,00   | 0,00      | 0,00      |
#       | MONTHLY | 100000,00 | 100000,00  | 0,00      | 0,00      |
