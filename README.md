# MidiaConverte - Sistema de Download e Conversão de Mídia

## Visão Geral

### Nome do Sistema
**MidiaConverte**

### Objetivo
Baixar vídeos das redes sociais (**Instagram**, **YouTube**, **Twitter** e **TikTok**) através de computador ou dispositivo móvel, oferecendo opções de formato e qualidade de saída.

### Funcionalidade Principal
Permitir que usuários baixem vídeos de redes sociais em formatos e qualidades personalizados.

---

### Plataformas Suportadas
- ✅ **YouTube**
- ✅ **TikTok**
- ✅ **Twitter/X**
- ✅ **Instagram**

### Formatos de Saída

#### Vídeo
| Formato | Descrição |
|---------|-----------|
| **MP4** | Formato universal |
| **WEBM** | Formato web otimizado |

#### Áudio
| Formato | Descrição |
|---------|-----------|
| **MP3** | Formato universal |
| **AAC** | Formato de alta qualidade |

### Qualidades de Vídeo

| Qualidade | Resolução |
|-----------|-----------|
| **1080p** (Full HD) | 1920x1080 |
| **720p** (HD) | 1280x720 |
| **360p** (SD) | 640x360 |


---

<details>
  <summary>Arquitetura</summary>

  ### Event-Driven (EDA)
  *Arquitetura focada em eventos.*
  
  - O foco dessa arquitetura é implementar a comunicação entre componentes através de eventos,diferente do modelo tradicional que se baseia em Request/Response.No EDA,um serviço publica um evento para que outro serviço consuma esse evento,não existe a preocupação de quem irá consumir esse evento ou se ao menos terá uma resposta.
  - O ponto positivo dessa arquitetura é que nenhum serviço terá um caráter bloqueante caso algo der errado,basicamente se um serviço A tem um problema, o serviço B pode continuar o processo normalmente.
    - **Conversao**:Indepente do serviço de Donwload
    - **Compressao**:Independete do serviço de Conversão e Downlad
    - *Cada serviço tem a sua fila de mensageria,então caso o serviço anterior caia,ele pode continuar processando os jobs da sua fila e assim por diante.*
  
</details>

<details>
  <summary>Descrição dos Serviços</summary>

  #### 1. Eureka Server - Service Discovery

**Responsabilidades:**
- Todos os microsserviços se registram no Eureka
- Permite que serviços localizem uns aos outros dinamicamente
- Monitora disponibilidade de cada instância
- **Load balancing**: Distribui requisições entre múltiplas instâncias

**Tecnologia:** Spring Cloud Netflix Eureka



#### 2. API Gateway - Porta de Entrada

**Responsabilidades:**
- **Ponto único de entrada**: Todas as requisições HTTP passam pelo gateway
- **Rate limiting**: Limite de requisições por usuário/IP)
- **Validação de entrada**: 
  - URL válida (começa com http/https)
  - Plataforma suportada
  - Formato válido
- **Roteamento**: Direciona requisições para Orchestrator Service através do RabbitMQ

**Tecnologia:** Spring Cloud Gateway

#### 3. Orchestrator Service
Microsserviço - Coordenação

**Responsabilidades:**
- Monitora eventos
- Garante consistência
- Gerencia falhas e retries
- **Cleanup**: Deleta arquivos temporários após processamento

#### 4. Download Service
Microsserviço - Download de Plataformas

**Responsabilidades:**
- Realizar download da mídia através da URL submetida pelo usuário.
  - **YouTube**: yt-dlp com suporte a qualidades
  - **TikTok**: yt-dlp (remoção de marca d'água quando possível)
  - **Twitter/X**: yt-dlp
  - **Instagram**: yt-dlp (apenas posts públicos)
- **Metadados**: Extrai título, duração, thumbnail

#### 5. Conversion Service
Microsserviço - Conversão e Validação

**Responsabilidades:**
- **Validação de mídia**:
  - Integridade do arquivo (não corrompido)
  - Formato válido
  - Tamanho máximo
  - Extração de metadados técnicos (codec, bitrate, fps)
- Conversão de formato
- Redimensionamento: 4K → 1080p/720p/360p


#### 6. Compression Service
Microsserviço - Otimização

**Responsabilidades:**
- **Compressão de vídeo**:
  - Recodificação com H.265/HEVC (melhor compressão)
  - Redução de bitrate mantendo qualidade visual
  - Redução típica: 40-60% do tamanho
- **Otimização por tipo**:
  - **Vídeo MP4**: Sempre comprime
  - **Áudio MP3/AAC**: Não comprime (já otimizado)

---
  
</details>

<details>
  <summary>Bibliotecas Compartilhadas</summary>

#### MediaFlow MinIO Client (JAR)

**Responsabilidades:**
- Encapsula SDK do MinIO e métodos de comunicação.
  - upload
  - download
  - delete (cleanUp - limpa todos os arquivos intermediários compartilhados entre os serviços,exceto o arquivo final)
- **Estratégia de atualização da biblioteca**:
  - Publicação em repositório Maven e atualização dos serviços por CI/CD
  
</details>

<details>
<summary>Rastreio e Logs</summary>

#### Tabela processing_jobs

 - **Objetivo:** Armazenar metadados e estado de cada job de processamento
 - **Problemas resolvidos:**
   - Armazenar estado persistente de cada job de processamento
   - Permitir consulta de status em tempo real
   - Garanti recuperação após falhas

```sql
CREATE TABLE processing_jobs (
    id VARCHAR(36) PRIMARY KEY,
    source_url VARCHAR(500) NOT NULL,
    platform VARCHAR(20) NOT NULL,
    target_format VARCHAR(10) NOT NULL,
    quality VARCHAR(10),
    status VARCHAR(20) NOT NULL,
    progress INT DEFAULT 0,
    current_step VARCHAR(100),
    download_url VARCHAR(500),
    error_message TEXT,
    created_at TIMESTAMP NOT NULL,
    completed_at TIMESTAMP
);
```


</details>


