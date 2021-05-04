<template>
  <div class="modal-body">
    <div class="row justify-content-center">
      <div class="col-md-8">
        <b-alert show data-cy="loginError" variant="danger" v-if="authenticationError">
          <strong>Erro ao fazer login!</strong> Confira suas credenciais e tente novamente.
        </b-alert>
      </div>
      <div class="col-md-8">
        <b-form role="form" v-on:submit.prevent="doLogin()">
          <b-form-group label="Usuário" label-for="username">
            <b-form-input id="username" type="text" name="username" autofocus placeholder="Seu usuário" v-model="login" data-cy="username">
            </b-form-input>
          </b-form-group>
          <b-form-group label="Senha" label-for="password">
            <b-form-input
              id="password"
              type="password"
              name="password"
              v-model.trim="name"
              placeholder="Sua senha"
              v-model="password"
              data-cy="password"
            >
            </b-form-input>
          </b-form-group>
          <b-form-checkbox id="rememberMe" name="rememberMe" v-model="rememberMe" checked>
            <span>Lembrar-me</span>
          </b-form-checkbox>
          <div>
            <b-button class="btn btn-success" data-cy="submit" type="submit" variant="primary">Entrar</b-button>
          </div>
        </b-form>
        <p></p>
        <div>
          <b-link :to="'/account/reset/request'" class="alert-link" data-cy="forgetYourPasswordSelector">Esqueceu sua senha?</b-link>
        </div>
        <!--        <div>-->
        <!--          <b-alert show variant="warning">-->
        <!--            <span>Não possui uma conta ainda?</span>-->
        <!--            <b-link :to="'/register'" class="alert-link">Crie uma nova conta</b-link>-->
        <!--          </b-alert>-->
        <!--        </div>-->
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./login-form.component.ts"></script>
