import './App.css';
import '@material-ui/core'
import { Button, Input, InputLabel } from '@material-ui/core';



function App() {
  return (
      <div className="container">
        <form className="login-form">
        <div class = "form-group" className="login-container">
        <InputLabel for = "InputEmail">Digite seu email: </InputLabel> 
        <Input type = "email" id = "InputEmail"  />
        </div>
        <div className="login-container">
        <InputLabel for = "InputPassword">Digite sua senha:</InputLabel> 
        <Input type = "password" id = "exampleInputPassword1" />
        </div>
        <div className="button-container">
        <Button variant="contained" color="primary">Entrar</Button>
        </div>
        <div className="button-container">
        <Button variant="outlined" color="primary">Novo usuário</Button>
        </div>
        </form>
      </div>
     
  );
}

export default App;
