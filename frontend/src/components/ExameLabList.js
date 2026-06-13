import React, { useState } from 'react';
import { exameService } from '../services/api';

function ExameLabList({ exames = [], atendimentos = [], onUpdate }) {
  const [form, setForm] = useState({ descricao: '', atendimentoId: '' });

  const deletar = async (id) => {
    await exameService.deletar(id);
    if(onUpdate) onUpdate();
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const payload = {
      descricao: form.descricao,
      atendimento: { id: form.atendimentoId }
    };
    await exameService.criar(payload);
    setForm({ descricao: '', atendimentoId: '' });
    if(onUpdate) onUpdate();
  };

  return (
    <div className="section-container">
      <h2>Exames de Laboratório</h2>
      
      <form onSubmit={handleSubmit} className="form-container">
        <input type="text" placeholder="Descrição do Exame" value={form.descricao} onChange={e => setForm({...form, descricao: e.target.value})} required />
        <select value={form.atendimentoId} onChange={e => setForm({...form, atendimentoId: e.target.value})} required>
          <option value="">Selecione o Atendimento</option>
          {atendimentos.map(a => (
            <option key={a.id} value={a.id}>Data: {a.data} | Profissional: {a.profissionalDeSaude?.nome}</option>
          ))}
        </select>
        <button type="submit">Registrar Exame</button>
      </form>

      <table>
        <thead>
          <tr><th>ID</th><th>Descrição</th><th>Atendimento (Data/Profissional)</th><th>Ações</th></tr>
        </thead>
        <tbody>
          {exames.map(e => (
            <tr key={e.id}>
              <td>{e.id}</td>
              <td>{e.descricao}</td>
              <td>{e.atendimento?.data} - {e.atendimento?.profissionalDeSaude?.nome}</td>
              <td><button className="btn-danger" onClick={() => deletar(e.id)}>Excluir</button></td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default ExameLabList;
