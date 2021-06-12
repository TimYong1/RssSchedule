package com.sx.trackdispatch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amap.api.maps.model.LatLng

class TrackMapViewModel : ViewModel() {
    val lines = MutableLiveData<MutableList<LatLng>>()
    val lines1 = MutableLiveData<MutableList<LatLng>>()
    val latitude = 28.213675
    val longitude = 112.891641

    init {
        lines1.value = mutableListOf()
        for (i in 0..100){
            lines1.value?.add(LatLng(latitude+(0.1*i),longitude+(0.1*i)))
        }
        lines.value = mutableListOf(
            //南京南->句容
            LatLng(31.968289126231518, 118.79656677864189),
            LatLng(31.971426888539746,118.80388518581384),
            LatLng(31.97323577456169,118.80809893610416),
            LatLng(31.97560433745262,118.81317635768248),
            LatLng(31.977586056363243,118.81932129843048),
            LatLng(31.979696278306577,118.82444968197922),
            LatLng(31.980212734937947,118.83018692696392),
            LatLng(31.980815643187135,118.83501087979468),
            LatLng(31.981160322915944,118.84044369406142),
            LatLng(31.98146177442267,118.845369570833),
            LatLng(31.98151523930349,118.84615545806092),
            LatLng(31.981300242040334,118.84940629533135),
            LatLng(31.98035265550725,118.8531131081265),
            LatLng(31.979146828963625,118.85631164232205),
            LatLng(31.977380150707344,118.8591547838292),
            LatLng(31.97440868726406,118.86245524196555),
            LatLng(31.971996862132755,118.86570473813153),
            LatLng(31.968722594341756,118.8683453728615),
            LatLng(31.965406113027,118.8709860075915),
            LatLng(31.962003039749966,118.87372722515782),
            LatLng(31.959977747378986,118.87580996042226),
            LatLng(31.95707740541269,118.88044749972967),
            LatLng(31.952639670798064,118.88430585733158),
            LatLng(31.94794340379704,118.88765727743834),
            LatLng(31.943678207008684,118.89268507815075),
            LatLng(31.938291804515266,118.89842232313545),
            LatLng(31.933249945915897,118.90380417543177),
            LatLng(31.922726024674592,118.91556566176087),
            LatLng(31.918368591165777,118.91913434079413),
            LatLng(31.913851576993533,118.92476966183801),
            LatLng(31.91087578165881,118.92852609549911),
            LatLng(31.905135662795168,118.93347208883795),
            LatLng(31.899660471669037,118.93747930903767),
            LatLng(31.88702837678315,118.95218183750127),
            LatLng(31.88359051647512,118.95965581279341),
            LatLng(31.88253373866484,118.98300846518207),
            LatLng(31.883854709033248,119.01352261551797),
            LatLng(31.889142955832682,119.04434924319877),
            LatLng(31.894959221130065,119.07237162140248),
            LatLng(31.89971853841151,119.10195236301716),
            LatLng(31.90659178792794,119.13557921686161),
            LatLng(31.910821137233246,119.1642265497551),
            LatLng(31.920863782113422,119.20314808410434),

            //句容->金坛
            LatLng(31.924267240916905,119.22526960257625),
            LatLng(31.913711555116958,119.25566841792647),
            LatLng(31.901199797240917,119.27915920407703),
            LatLng(31.885557138592635,119.31186069582718),
            LatLng(31.858176661964524,119.33903549599667),
            LatLng(31.8339191110185,119.37450100408937),
            LatLng(31.80300037563509,119.41595454370537),
            LatLng(31.774421087713627,119.44451068149388),
            LatLng(31.73956591363009,119.47076414288254),
            LatLng(31.726638134941908,119.49563626665216),
            LatLng(31.722328474546906,119.51175634255588),
            LatLng(31.7207610682149,119.53248311236388),
            LatLng(31.713708552598874,119.5651859452185),
            LatLng(31.71337998492637,119.57758043286427),
            //金坛->武进
            LatLng(31.714186570848796,119.58866063811523),
            LatLng(31.713389111821883,119.60065681772912),
            LatLng(31.712751367836027,119.61902592585336),
            LatLng(31.700632257731197,119.63364664694342),
            LatLng(31.692179205258615,119.64976672284716),
            LatLng(31.683406979920065,119.66982294041291),
            LatLng(31.67989762939302,119.69362888611732),
            LatLng(31.675909933193516,119.71555996775241),
            LatLng(31.673049188475856,119.72643230180827),
            LatLng(31.67336817255303,119.7402161737),
            LatLng(31.67495121710041,119.75044880091677),
            LatLng(31.672773525199283,119.77649439111032),
            LatLng(31.669211268143915,119.80393472996774),
            LatLng(31.665648874460903,119.82370126896484),
            LatLng(31.66743065909382,119.84928149590228),
            LatLng(31.662679918448664,119.87160686225579),
            LatLng(31.66327348894618,119.89276949102127),
            LatLng(31.658325049694074,119.91927910336645),
            LatLng(31.65911727429654,119.93904698346803),
            LatLng(31.65792893485993,119.95253447126868),
            //武进->江阴
            LatLng(31.659756528417635,119.96225881988579),
            LatLng(31.663270064511877,119.96826294466292),
            LatLng(31.663908148590533,119.97558001073037),
            LatLng(31.666623666700623,119.98665082824995),
            LatLng(31.66981735228364,119.99847132317636),
            LatLng(31.670774979991055,120.01554492436858),
            LatLng(31.674607674758143,120.02811643780632),
            LatLng(31.680196642640368,120.04087436476739),
            LatLng(31.684826659971634,120.04819277193934),
            LatLng(31.69392738888425,120.05945000298227),
            LatLng(31.70286634567398,120.06958204736267),
            LatLng(31.70989002240647,120.07521066288413),
            LatLng(31.71803002180935,120.08815634447305),
            LatLng(31.726648401230843,120.09960267124829),
            LatLng(31.732234234444526,120.11179733432589),
            LatLng(31.73829762482387,120.11742729095184),
            LatLng(31.741010966131476,120.1208041920438),
            LatLng(31.751219214883996,120.13226795317735),
            LatLng(31.754960779094226,120.14199364289895),
            LatLng(31.759488998488244,120.15449944221696),
            LatLng(31.773271849406317,120.17094138319698),
            LatLng(31.781146353783704,120.18506723673224),
            LatLng(31.791382093121452,120.20011979346633),
            LatLng(31.797483782765298,120.21355095487861),
            LatLng(31.804765843743997,120.22883552268851),
            LatLng(31.81106381083726,120.24087729985487),
            LatLng(31.817557356220576,120.25083500065229),
            LatLng(31.82896952750444,120.25217130612798),
            LatLng(31.83506987394106,120.25430846126716),
            LatLng(31.84588976163939,120.26658090840485),
            LatLng(31.85828259604111,120.27746531240108),
            LatLng(31.866149854393946,120.29020178169043),
            LatLng(31.875196314465995,120.30201288888543),
            LatLng(31.878145931297716,120.30895981011517),
            //江阴->常熟
            LatLng(31.899550030900873,120.34646245591982),
            LatLng(31.902387291908234,120.37367883032833),
            LatLng(31.903603234138515,120.39325493248865),
            LatLng(31.90117133361409,120.41235360145244),
            LatLng(31.894685951424744,120.43193104471725),
            LatLng(31.892658037144077,120.45007484728809),
            LatLng(31.897928699631056,120.46821864985894),
            LatLng(31.90157665483033,120.49161421759109),
            LatLng(31.906846806690538,120.52074032478554),
            LatLng(31.901577793370564,120.55081995726847),
            LatLng(31.896307339805407,120.57039740053325),
            LatLng(31.881712677708077,120.5942704014619),
            LatLng(31.863060893673477,120.61480271111965),
            LatLng(31.84197073228905,120.62435137504932),
            LatLng(31.83061258166566,120.63581111286939),
            LatLng(31.80829781844859,120.64774828388596),
            LatLng(31.781107592844435,120.65634208142265),
            LatLng(31.762029539300308,120.67353235870502),
            LatLng(31.744164455458726,120.68928899529345),
            LatLng(31.725078786517294,120.71507172900805),
            LatLng(31.71817376309976,120.73273943948689),
            LatLng(31.70273855455608,120.7461075689884),
            LatLng(31.693800726555914,120.76329784627077),
            //常熟->太仓
            LatLng(31.693636407602046,120.78643189862848),
            LatLng(31.693251854468546,120.80676170150905),
            LatLng(31.683257478586725,120.81715257905499),
            LatLng(31.677106031774258,120.83974080188749),
            LatLng(31.663649034475288,120.85781084371168),
            LatLng(31.655573900278423,120.87407441779526),
            LatLng(31.650190087617926,120.88943408745628),
            LatLng(31.645575469037684,120.90072752832027),
            LatLng(31.642498548812238,120.92602746442044),
            LatLng(31.6374987644424,120.938224809707),
            LatLng(31.634806373679304,120.95493899489736),
            LatLng(31.632883515942844,120.98475577090132),
            LatLng(31.635191168820842,121.01321669027145),
            LatLng(31.63173023896615,121.02541403555803),
            LatLng(31.63788354844422,121.0439360295935),
            LatLng(31.640960621389937,121.06426583247408),
            LatLng(31.642114925644925,121.07781903439447),
            LatLng(31.632498711254062,121.09679298064123),
            LatLng(31.622882644573618,121.1130565547248),
            LatLng(31.605569849512307,121.13428892092348),
            LatLng(31.591333691216924,121.14648626621003),
            LatLng(31.577094214674062,121.16094337255298),
            LatLng(31.571320803242003,121.16591216466807),
            LatLng(31.560158013101287,121.17946536658846),
            LatLng(31.549764184825758,121.18895233971183),
            LatLng(31.538213620804363,121.19301856850885),
            LatLng(31.52974351813073,121.20747299264282)
        )
    }
}