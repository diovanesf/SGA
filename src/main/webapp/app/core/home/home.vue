<template>
  <div class="home row">
    <div class="col-md-3">
      <span class="hipster img-fluid rounded"></span>
    </div>
    <div class="col-md-9">
      <h1 class="display-4">Bem vindo!</h1>
      <p class="lead"><strong>Sistema de Gerenciamento de Amostras</strong></p>
      

      <div>
        <div class="alert alert-success" v-if="authenticated">
          <span v-if="username">Você está logado com o usuário "{{ username }}"</span>
        </div>

<<<<<<< Updated upstream
        <div class="alert alert-warning" v-if="!authenticated">
          <span>Você quer fazer </span>
          <a class="alert-link" v-on:click="openLogin()">login</a
          ><span
            >, você pode usar as contas padrões: <br />- Administrador (login="admin" and password="admin") <br />- Usuário (login="user"
            and password="user") <br />- Aluno (login="aluno" and password="user") <br />- Professor (login="professor" and
            password="user").</span
          >
        </div>
        <!--        <div class="alert alert-warning" v-if="!authenticated">-->
        <!--          <span>Não possui uma conta ainda?</span>&nbsp;-->
        <!--          <router-link class="alert-link" to="/register">Crie uma nova conta</router-link>-->
        <!--        </div>-->
=======
        

>>>>>>> Stashed changes
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./home.component.ts"></script>
