CREATE TABLE PAIS (
                      ID NUMBER GENERATED AS IDENTITY PRIMARY KEY,
                      NOME VARCHAR2(100) NOT NULL,
                      CODIGO_ISO VARCHAR2(5) NOT NULL
);

CREATE TABLE ESTADO (
                        ID NUMBER GENERATED AS IDENTITY PRIMARY KEY,
                        NOME VARCHAR2(100) NOT NULL,
                        SIGLA VARCHAR2(25) NOT NULL,
                        PAIS NUMBER NOT NULL,
                        CONSTRAINT ESTADO_PAIS_FK FOREIGN KEY (PAIS) REFERENCES PAIS(ID)
);

CREATE TABLE CIDADE (
                        ID NUMBER GENERATED AS IDENTITY PRIMARY KEY,
                        NOME VARCHAR2(100) NOT NULL,
                        LATITUDE NUMBER(9,6),
                        LONGITUDE NUMBER(9,6),
                        ESTADO_ID NUMBER NOT NULL,
                        CONSTRAINT CIDADE_ESTADO_FK FOREIGN KEY (ESTADO_ID) REFERENCES ESTADO(ID)
);

CREATE TABLE BAIRRO (
                        ID NUMBER GENERATED AS IDENTITY PRIMARY KEY,
                        NOME VARCHAR2(100) NOT NULL,
                        POPULACAO NUMBER(10),
                        AREA_KM2 NUMBER(10,2),
                        CIDADE_ID NUMBER NOT NULL,
                        CONSTRAINT BAIRRO_CIDADE_FK FOREIGN KEY (CIDADE_ID) REFERENCES CIDADE(ID)
);

CREATE TABLE REGIAO_MONITORADA (
                                   ID NUMBER GENERATED AS IDENTITY PRIMARY KEY,
                                   NOME VARCHAR2(100) NOT NULL,
                                   LATITUDE NUMBER(9,6),
                                   LONGITUDE NUMBER(9,6),
                                   ALTITUDE_MEDIA NUMBER(10,2),
                                   DECLIVIDADE_PERCENTUAL NUMBER(5,2),
                                   COBERTURA_VEGETAL_PERCENTUAL NUMBER(5,2),
                                   IMPERMEABILIZACAO_PERCENTUAL NUMBER(5,2),
                                   DISTANCIA_RIO_METROS NUMBER(10,2),
                                   TIPO_SOLO VARCHAR2(50),
                                   NIVEL_URBANIZACAO VARCHAR2(20),
                                   ATIVA VARCHAR2(1),
                                   BAIRRO_ID NUMBER NOT NULL,
                                   CONSTRAINT REGIAO_BAIRRO_FK FOREIGN KEY (BAIRRO_ID) REFERENCES BAIRRO(ID)
);

CREATE TABLE USUARIO (
                         ID NUMBER GENERATED AS IDENTITY PRIMARY KEY,
                         NOME VARCHAR2(100) NOT NULL,
                         EMAIL VARCHAR2(100) NOT NULL,
                         SENHA VARCHAR2(100) NOT NULL,
                         TIPO_USUARIO VARCHAR2(30) NOT NULL,
                         ATIVO VARCHAR2(1),
                         CRIADO_EM TIMESTAMP,
                         CONSTRAINT USUARIO_EMAIL_UK UNIQUE (EMAIL)
);

CREATE TABLE CONSULTA_RISCO (
                                USUARIO_ID NUMBER(10) NOT NULL,
                                PROTOCOLO VARCHAR2(36) NOT NULL,
                                REGIAO_MONITORADA_ID NUMBER(10) NOT NULL,
                                PROMPT VARCHAR2(500) NOT NULL,
                                LATITUDE NUMBER(9,6),
                                LONGITUDE NUMBER(9,6),
                                RUA_REGIAO VARCHAR2(120),
                                NIVEL_RISCO VARCHAR2(20),
                                RESPOSTA_IA VARCHAR2(500),
                                CRIADO_EM TIMESTAMP,
                                CONSTRAINT CONSULTA_RISCO_PK PRIMARY KEY (USUARIO_ID, PROTOCOLO),
                                CONSTRAINT CONSULTA_RISCO_USUARIO_FK FOREIGN KEY (USUARIO_ID) REFERENCES USUARIO(ID),
                                CONSTRAINT CONSULTA_RISCO_REGIAO_FK FOREIGN KEY (REGIAO_MONITORADA_ID) REFERENCES REGIAO_MONITORADA(ID)
);