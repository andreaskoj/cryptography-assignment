package Cryptography;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigInteger;

public class FiatShamir {

    //	n=18620748682798751676034731540554549529657921592487639609579044500790631406014218785584399190138533508004957022066697550301376892112509446972028177795133189219220061230805270751508808844185248878534401189156295089769401734148116378773648803137607468678252504291910234875497976749594848267003611612328476774860495412526105928604294356291197740114446937215861863996201483967215154785080811736234096750294977010433350887358932302143589051636190683562245820295666038238386015731107739923955953153994745921486857546914533112517286638419734614432840404878624558738161295214597734243015467077486514206892184104958992831443031
    //	X=14067701320090457911428017910087880969107592693094054383302199220806326416593523648010709182867044086596495979205789937284822577911161580145562761619825373760131239618931343241299751796179031432760149032291610975450998860648243600831646419987328222448144778891177894194171582615417271584617728361699478561054315638304803425770484101939102668748383114389920902317500723339971757136902981511965347069180587905508002250897871979980110053891954522369535900408814948118839736739545526147472397541346664044963376336770828961406670679208833562477970963331355077509827260295042357583233919906379474487062062865333087026634400
    //	R=1908672112978569522195824761395119138841948332359178620731530954043035198516429177452822425710356818449410873848375291150050858652848762650207761448896286569139163665742946557333377235577600788789587456434241621982332830055568596862110399860960713739494040084482875614159920122468641379147521657352542390485133549374396124519741963130647334279610571249047741394892595375994976856542397855821836791783110235401667974825014166792093158619067476540689260220542907700406935701715474716860592181662913394475749634193298295200726285558183622769326907932488323665787373965711412346328101244830828015727881532242781128902500,c=1,s=2041023389528780268055418607841811462773806753653475623676259011375637348249570285374978429986271690711435317850489787015736732402399447900056102342199144942982993334813128621893286922586059586097793430268320669833263104518459648296115558629804954925272019283903205751084293924793740919178300865546973158138117481012141824742488044103744298612316235721734965007837877694782530812824718770570164432376487685065660549457896420500162874851498402250487699512093202410467425332458335056749924587486316631096595408374605172962244287275925874712786616236170912281611344977886486389646316393352557037684828125782335982695806
    //	R=3655578432657577605777222435005358338537452708363436122054716183691352982983748352992237533156638533944408573888938155817771065717066695412230588336759564040743425791266816570967717157632302959803245626746083733232003023996355433648187136014175414590716939631934465493367733354019751178884252278803255239683322351655533813812081149967580080549059011097731994182831985760051689906081307779704091842707500719011575054069106328978423765115945871958050737447226064815389002408879746119301206202419040203249509404655052387826775154577603654861624049832689373854225335779168468697257940165926550484442007743334818671992841,c=1,s=1596367806536975679604008793309950543773690545658931310108912670386568529858626680530808748280246923120333007602436002832172543888778844708688362906604514802652823435934508330988953352832833864648106467142700413793555409173499949485232709720545581704485754495113186281631504930548633158818109237080906805193371891982823523827630089418129987366126868186546908505183973354508964890989113569570716353611134122885637338245757846774411683203951068030759628763990747563581455259463104671108972302068112839418168975776960483396562814064699861248451468922368840567342555003265615006866816886637778646368637127279438934755224
    //	R=839163849173111189081040668641890426651298667984526382683725255498897129384810599171999799527207511463215875491334310414824406753636046340084686606265460130351735894631606096439287526373074623544976122175321003302627917291517056268894027098387190668358774288296075657546575408596279634846763489947368245853925707762453568455796616336355512586853175804782260955581450630556368496809433873228612612607242879501525205400278990077658780040893689917307620915739647136893585348713111315540963991339108010189617391640659233036232255408870075331938218032100388773293094975936671229469465604293007583825561187531161510033450,c=0,s=139498790431931211297786887312868510019680196408344887727878502109059123201271062102411025782214193161688208824293538947985307003427186886255118001406874746064066439791060555483378172545995854703249314981672286110102370251530478712078491891057114064361832771467034926829908376830841242602428350472077245920191
    //	R=6566391356610082207898065440968041430972878027337849347810127159933426528891802683933949156901239122332994316974614504371895045296252364943491909758193075823852608568818387382642923159015528363462382344172043457516874058547188105644593357842423451553099499081269495285079844307133868667514879550734966140140708196497170667956432541832469910205486337171097435575385368360637717910348623365976681055444088844074548532927574526769182399052623616164746598134920936231794657816525713394648651607253066452086210368124792840939646345826158492805914849365231644065742762326837242898634224213371685401881486081351133574766505,c=0,s=158704568426396704920675777316985420078727097528600607753895218645260281548301575376701444877734661462766639885267799855725660698193936302495085089806542386962070949538556121349712810453186297344386756797140622414976285079481518557548012075501373752264899123072676982879950104385437791887152661119224874487744
    //	R=12498054414019456557780495945403918565164284521184562321191908578743354143687849385999101702344890478393596718647363773325486626451557995006659546348686625509837003099934553743763626238982257686351461441883684848469518755654182752870943729071139534833373539239398103928274283473555116102243765547226690449866626908891369953046618106029232000134568178355566647016344587310299382295762711200769422628743429383995818026657154499089431625263080850118850398807702226229313183401772197979192677653771717236792009678223001648839578531210299941373449882060628159781901221569934474408070619288021450149468244813664667395869869,c=1,s=5964319521251699524270328272655420209007570990226335300369513566911440664877290574019846829137681686917230343633025640373402336533579008716831926617253756356617996261431594210906744504971903472980900782734924960944654867268172500243009382399281984685537995759307510631276633661702858337585972704411746526452835901672761473551597427867080147829815266440512284151355393703174423415729855349706173894206172803068908349249431514022898620732812369554480321585426161199530987123192273689884228069085873547762480485203205459900536704947227769173315400432341150280348914376433526852061790393474825891452707690325890164767347
    //	R=6665590093956034041526765270816357237783939124288835045298910508150251454243631455433561110175628049329969606630817893591895927407361703585791441582746326188514323652470114518389171427882055448709940312668423245410645143474050906566748605227194875906066706230360824163123557126312220344978761350797152995102666146185023948856993115580007956373191546198361938866199574921821732534873089187280319529984104556205386318754590917650347737380810272053780634576507253062321005859379086420457732551388853319888655495848879058304075142805293462960643093817605136094065042321817878001058862179086238375837814225987265267264049,c=0,s=81643065192066582781018773913243089667805124563327081676837753773805725361857759363997059428551148413601431969942072831789005938012453878265716957951437165371213997560615817319128918725646616254245027461805880516057621912640625494546388782426140695185408114872696534850681347470410336751638532386023150623993
    //	R=4613654991000176192208683134004849799054843593224580033414167086774174542543671212718938385311198144454539239472490763240338554659467901962707158960400457060705263782951454598391713841767430163299042784024524058521559654777235530269117692513646198749514228523931374683657349672710307492336640341733229108599395088016569965344871689099394783065491032714821413161865606196278341210794292858838104863753416023727263208323775035865343842640453640243913188258784912984824307484916009836022888178641122117642454107198293254313591409816716842521331711143544455817385532282406073038291898336698229240406846267788611327681,c=0,s=2147942036229138142384252599475068837821845820838537331023153020219399941617472224442834652204564477831161903652921043060016596383765690509218033242383529362293828885186192300402002791965951505499664041505261407370815499762451788474030604570883085700021619262242175883053714663830853835827864235269443443041
    //	R=6659630534022429942942970479232814691222584180358596847832173692377208193626798890999603089194291582599330273867552886618103794966955284675318341737693908007914869498623827692984198551219941993603547997605093069737243903211377990461818693757920566989462903153315353492740602046392000269969497088621461365924271398439389724789384373494699326744775263047093287150752815212366399008073183641124790589420352223353149595832983215176919551731982993161520831564226558579625296963703988461541242010502205930642848892670844390048155442801304099478438439436529996561994729070924953095290021431729069747531987471735694736220816,c=1,s=17486183820986767934867948896373521916381306056326721193403416038532648402394044953344496936196201991917246713872892733092607725063530777863626983536920266957880751396157364455024659697527067845287330201287785510920411943287496301004181178729611717654049007460996463754830367672921694864535501730607554942155559761464446062632133281812324263784731378797769350640926389658843073984161511763907584716148912016197455749588775708869369694224491714230010539657307526270886184807987949413043200169113633302004290604542006095162250300734630593775044675588244103529251623929763488063397985212758173098180704688167279211688554
    //	R=3655578432657577605777222435005358338537452708363436122054716183691352982983748352992237533156638533944408573888938155817771065717066695412230588336759564040743425791266816570967717157632302959803245626746083733232003023996355433648187136014175414590716939631934465493367733354019751178884252278803255239683322351655533813812081149967580080549059011097731994182831985760051689906081307779704091842707500719011575054069106328978423765115945871958050737447226064815389002408879746119301206202419040203249509404655052387826775154577603654861624049832689373854225335779168468697257940165926550484442007743334818671992841,c=0,s=60461379678746809141418950395983812176460078064101550606937768584311853520495492674877302159033758917629781110600391146887274125940388254345885634650208281348169948685248067273526531513163143887098743927552187815681239751767271085652627813110360524527914557457584754650261725305205653177792647741986058932221
    //	R=2832856415864680149904925738365340233739673780483525948809027690396214413311139466099945040820052454021851481232272661288687637305702309679699840017746016866974690244058917733224671501246434240551495188926846963587189077562585427498270628022819114520995754054455619412516285944495939590493296639916205108883226139860520118511401251127808011079121638290777116067408807457245975815764511533650438098521095505361124605296406357949980407144049996330531457266855667830258785687355895903456732701914066639073832563084072315934070256242193101423041740834578933553579494683165473968066198234520322449106667570362360833024,c=0,s=1683109151500484281620557008728453884952064183060739128916040061871770103870537225610110450897042441114010874171871658638747426607729274416675722807489150686753868409150180099927723758738260573142597008202033104454102123401993270774830058443829662961109949330436177107760356623876647443089728556910409111968

    public static class ProtocolRun {

        public final BigInteger R;
        public final int c;
        public final BigInteger s;

        public ProtocolRun(BigInteger R, int c, BigInteger s) {
            this.R = R;
            this.c = c;
            this.s = s;
        }

    }

    public static void main(String[] args) {

        String filename = "input.txt";
        BigInteger N = BigInteger.ZERO;
        BigInteger X = BigInteger.ZERO;
        ProtocolRun[] runs = new ProtocolRun[10];

        try {

            BufferedReader br = new BufferedReader(new FileReader(filename));
            N = new BigInteger(br.readLine().split("=")[1]);
            X = new BigInteger(br.readLine().split("=")[1]);

            for (int i = 0; i < 10; i++) {

                String line = br.readLine();
                String[] elements = line.split(",");

                BigInteger R = new BigInteger(elements[0].split("=")[1]);
                Integer c = Integer.parseInt(elements[1].split("=")[1]);
                BigInteger s = new BigInteger(elements[2].split("=")[1]);

                runs[i] = new ProtocolRun(R, c, s);
            }

            br.close();

        } catch (Exception err) {

            System.err.println("Error handling file.");
            err.printStackTrace();
            System.exit(1);

        }

        BigInteger m = recoverSecret(N, X, runs);
        System.out.println("Recovered message: " + m);
        System.out.println("Decoded text: " + decodeMessage(m));

    }

    public static String decodeMessage(BigInteger m) {
        return new String(m.toByteArray());
    }

    /**
     * Recovers the secret used in this collection of Fiat-Shamir protocol runs.
     *
     * @param N    The modulus
     * @param X    The public component
     * @param runs Ten runs of the protocol.
     * @return
     */
    private static BigInteger recoverSecret(BigInteger N, BigInteger X, ProtocolRun[] runs) {

        // TODO. Recover the secret value x such that x^2 = X (mod N).
        return BigInteger.ZERO;

    }
}
