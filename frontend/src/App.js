import React, { useState, useEffect } from 'react';
import './App.css';
import ProfissionalList from './components/ProfissionalList';
import AtendimentoList from './components/AtendimentoList';
import ExameLabList from './components/ExameLabList';
import { profissionalService, atendimentoService, exameService } from './services/api';

function App() {
  const [profissionais, setProfissionais] = useState([]);
  const [atendimentos, setAtendimentos] = useState([]);
  const [exames, setExames] = useState([]);

  const carregarTudo = async () => {
    try {
      const [profRes, atendiRes, exameRes] = await Promise.all([
        profissionalService.listar(),
        atendimentoService.listar(),
        exameService.listar()
      ]);
      setProfissionais(profRes.data);
      setAtendimentos(atendiRes.data);
      setExames(exameRes.data);
    } catch (error) {
      console.error("Erro ao carregar os dados", error);
    }
  };

  useEffect(() => {
    carregarTudo();
  }, []);

  return (
    <div className="App">
      <header className="App-header">
        <h1>Agenda de Clínica</h1>
      </header>
      <main>
        <ProfissionalList profissionais={profissionais} onUpdate={carregarTudo} />
        <AtendimentoList atendimentos={atendimentos} profissionais={profissionais} onUpdate={carregarTudo} />
        <ExameLabList exames={exames} atendimentos={atendimentos} onUpdate={carregarTudo} />
      </main>
    </div>
  );
}

export default App;
