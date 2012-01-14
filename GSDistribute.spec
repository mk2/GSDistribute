Name:		GSDistribute
Version:	1.0
Release:	1%{?dist}
Summary:	GSDistribute

Group:		Utilities
License:	Apache License 2.0
URL:		http://www.kuroichigo.net/
Source0:	GSDistribute-source-${version}.jar
BuildRoot:	%(mktemp -ud %{_tmppath}/%{name}-%{version}-%{release}-XXXXXX)
BuildArch:  noarch

#BuildRequires:	
#Requires:	

%description


%prep
jar xvf $RPM_SOURCE_DIR/GSDistribute-source-%version.jar

%build

%install
rm -rf $RPM_BUILD_ROOT
ant install -Ddestdir=$RPM_BUILD_ROOT/%_prefix/local/bin

%clean
rm -rf $RPM_BUILD_ROOT


%files
%defattr(-,root,root,-)
%doc
%_prefix/local/bin/GSDistribute-%{version}.jar



%changelog

