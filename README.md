# 🛒 Marketplace POO

> Sistema simples de marketplace desenvolvido em Java para praticar conceitos de **Programação Orientada a Objetos**.

---

## 📌 Sobre o Projeto

O sistema simula uma aplicação de marketplace com cadastro de usuários e gerenciamento básico de produtos. A estrutura foi criada para aplicar na prática os principais pilares da POO:

| Conceito | Descrição |
|---|---|
| 🧬 **Herança** | `Comprador` e `Vendedor` herdam de `Usuario` |
| 🔗 **Agregação** | `Vendedor` possui uma lista de `Produto`s |
| 🔒 **Encapsulamento** | Atributos privados com getters e setters |
| ⚡ **Métodos Estáticos** | Utilitários e contadores de instância |
| 📋 **Listas em Memória** | Coleções gerenciadas em runtime |

---

## 📁 Estrutura do Projeto

```text
📦 marketplace-poo/
├── 📂 src/
│   ├── 📄 Main.java         ← Ponto de entrada e menus
│   ├── 📄 Usuario.java      ← Classe base dos usuários
│   ├── 📄 Comprador.java    ← Usuário com saldo
│   ├── 📄 Vendedor.java     ← Usuário com produtos e extrato
│   └── 📄 Produto.java      ← Entidade de produto
└── 📂 docs/
    ├── 🖼️ diagram.png       ← Diagrama de classes
    └── 📄 requirements.md   ← Requisitos do projeto
```

---

## 🧩 Classes

### 👤 `Usuario`
Classe base para todos os usuários do sistema. Armazena dados comuns como **nome**, **email**, **telefone**, **tipo** e **id**.

### 🛍️ `Comprador` ← extends `Usuario`
Representa compradores na plataforma. Possui **saldo próprio** para realizar compras.

### 🏪 `Vendedor` ← extends `Usuario`
Representa vendedores na plataforma. Possui **extrato financeiro** e uma **lista de produtos** cadastrados.

### 📦 `Produto`
Representa os produtos disponíveis no sistema. Cada produto possui **nome**, **preço**, **estoque** e o **id do vendedor** responsável.

### ▶️ `Main`
Classe principal do programa. Contém os **menus interativos** e os fluxos de cadastro de usuários e produtos.

---

## ⚠️ Observações

> Os dados são armazenados **apenas em memória**. Ao encerrar o programa, todos os cadastros realizados durante a execução serão perdidos.

---

## 🚀 Como Executar

```bash
# Compile todos os arquivos
javac src/*.java -d out/

# Execute o programa
java -cp out/ Main
```

---

<div align="center">

### 🎓 Projeto Acadêmico — POO em Java

Desenvolvido com fins educacionais para consolidar os conceitos de<br>
**Programação Orientada a Objetos** na linguagem **Java** ☕

<br>

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![OOP](https://img.shields.io/badge/Paradigma-OOP-blue?style=for-the-badge)
![Status](https://img.shields.io/badge/Status-Em%20Desenvolvimento-yellow?style=for-the-badge)

<br>

*"Código limpo não é escrito seguindo um conjunto de regras.*
*É escrito por alguém que se importa."* — **Robert C. Martin**

</div>