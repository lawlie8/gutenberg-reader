import './App.css';
import GlobalFooter from './navbar/GlobalFooter';
import GlobalHeader from './navbar/GlobalHeader';
import GutenBergRoute from './security/GutenBergRoute';


function App() {
  return (
    <section>
          <GutenBergRoute />
              <GlobalHeader />
          <GlobalFooter />
    </section>
  );
}

export default App;
