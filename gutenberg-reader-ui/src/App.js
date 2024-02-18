import './App.css';
import GlobalFooter from './navbar/GlobalFooter';
import GlobalHeader from './navbar/GlobalHeader';
import Reader from './reader/Reader';

function App() {
  return (
    <section>
      <GlobalHeader />
      <Reader />
      <GlobalFooter />
    </section>
  );
}

export default App;
