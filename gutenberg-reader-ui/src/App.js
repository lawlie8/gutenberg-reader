import './App.css';
import GlobalFooter from './navbar/GlobalFooter';
import GlobalHeader from './navbar/GlobalHeader';
import GutenBergRoute from './security/GutenBergRoute';


function App() {
  return (
    <section>
          <GutenBergRoute />
          <GlobalFooter />
    </section>
  );
}

export default App;
